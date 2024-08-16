package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;
import springBootSecurity.com.example.springBootSecurity.configuration.ModelMapperConfig;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.dto.ProfileDto;
import springBootSecurity.com.example.springBootSecurity.model.request.User;
import springBootSecurity.com.example.springBootSecurity.model.request.UserUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.UserResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.UserRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.UserUpdateRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.UserResponseRepository;

import java.util.Optional;
import java.util.Random;

import static springBootSecurity.com.example.springBootSecurity.component.AppConst.MIN_ID;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository repo;
  private final UserUpdateRepository repoUpdate;
  private final UserResponseRepository repoResp;
  private final ModelMapperConfig mmc;
  private final StorageService storageService;
  private final AppProperties appProperties;

  /**
   * Generate OTP code.
   *
   * @return String
   */
  private static String generateOtp() {
    String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int length = 6;
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(allowedCharacters.length());
      char randomChar = allowedCharacters.charAt(randomIndex);
      sb.append(randomChar);
    }

    return sb.toString();
  }

  /**
   * Update.
   *
   * @param model ProfileDto
   * @return String
   */
  public ResponseEntity<String> update(ProfileDto model) {
    Optional<UserUpdate> user = repoUpdate.findById(model.getId());
    if (model.getId() < MIN_ID || user.isEmpty()) {
      throw new CustomException("User with ID `" + model.getId() + "` does not exists.");
    }

    Optional<UserResponse> checkEmail = repoResp.findByEmail(model.getEmail());
    if (checkEmail.isPresent() && !checkEmail.get().getId().equals(model.getId())) {
      throw new CustomException("Email already taken.");
    }

    UserUpdate map = mmc.map(user.get(), UserUpdate.class);
    map.setFirstName(model.getFirstName());
    map.setLastName(model.getLastName());
    map.setEmail(model.getEmail());
    map.setImage(model.getImage());
    if (!map.getPhone().equals(model.getPhone())) {
      map.setPhoneVerify(false);
    }
    map.setPhone(model.getPhone());
    try {
      repoUpdate.save(map);
      return ResponseEntity.status(HttpStatus.CREATED).body("Successfully updated");
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Read by ID.
   *
   * @param id Integer
   * @return UserResponse
   */
  public ResponseEntity<UserResponse> readById(Integer id) {
    if (id < MIN_ID) {
      throw new CustomException("User with ID `" + id + "` does not exists.");
    }
    Optional<UserResponse> user = repoResp.findById(id);
    return user.map(r -> ResponseEntity.ok().body(r)).orElseGet(() -> ResponseEntity.noContent().build());
  }

  /**
   * Update password.
   *
   * @param id   Integer
   * @param pass String
   * @return String
   */
  public ResponseEntity<String> updatePass(Integer id, String pass) {
    Optional<User> user = repo.findById(id);

    if (id < MIN_ID || user.isEmpty()) {
      throw new CustomException("User with ID `" + id + "` does not exists.");
    }
    try {
      String password = BCrypt.hashpw(pass, BCrypt.gensalt());
      user.get().setPassword(password);
      repo.save(user.get());
      return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Updated");
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Update image.
   *
   * @param id   Integer
   * @param file MultipartFile
   * @return String
   */
  public ResponseEntity<String> updateImage(Integer id, MultipartFile file) {
    Optional<User> user = repo.findById(id);
    if (id < MIN_ID || user.isEmpty()) {
      throw new CustomException("User with ID `" + id + "` does not exists.");
    }

    String img = user.get().getImage();
    String imagePath = storageService.storeImage(file);
    String oldImg = storageService.imgFromUrl(img);
    storageService.deleteImage(appProperties.getUploadImagePath() + oldImg);
    try {
      user.get().setImage(imagePath);
      repo.save(user.get());
      return ResponseEntity.status(HttpStatus.CREATED).body(imagePath);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Verify phone.
   *
   * @param id  Integer
   * @param otp String
   * @return String
   */
  public ResponseEntity<String> verifyPhone(Integer id, String otp) {
    Optional<User> user = repo.findById(id);

    if (id < MIN_ID || user.isEmpty()) {
      throw new CustomException("User with ID `" + id + "` does not exists.");
    }

    try {
      if (otp.equals(user.get().getOtp())) {
        user.get().setOtp(null);
        user.get().setPhoneVerify(true);
        repo.save(user.get());
        return ResponseEntity.ok().body("Phone successfully verified");
      }
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
    return ResponseEntity.badRequest().body("Phone unsuccessfully verified");
  }
}
