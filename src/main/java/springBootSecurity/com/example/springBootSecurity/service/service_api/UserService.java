package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.request.User;
import springBootSecurity.com.example.springBootSecurity.model.request.UserUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.UserResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.RoleRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.UserRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.UserUpdateRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.UserResponseRepository;

import java.util.Optional;

import static springBootSecurity.com.example.springBootSecurity.component.AppConst.MIN_ID;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserResponseRepository userResponseRepository;
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final UserUpdateRepository userUpdateRepository;


  public ResponseEntity<String> create(User model) {
    Optional<UserResponse> checkUserName = userResponseRepository.findByUserName(model.getUserName());
    if (checkUserName.isPresent()) {
      throw new CustomException("User name already taken.");
    }

    Optional<UserResponse> checkEmail = userResponseRepository.findByEmail(model.getEmail());
    if (checkEmail.isPresent()) {
      throw new CustomException("Email already taken.");
    }

    if (!roleRepository.existsById(model.getRoleId())) {
      throw new CustomException("Role with ID `" + model.getRoleId() + "` does not exists.");
    }
    try {
      String password = BCrypt.hashpw(model.getPassword(), BCrypt.gensalt());
      model.setPassword(password);
      userRepository.save(model);
      return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  public ResponseEntity<String> update(UserUpdate model) {
    Optional<UserResponse> user = userResponseRepository.findById(model.getId());
    if (model.getId() < MIN_ID || user.isEmpty()) {
      throw new CustomException("User with ID `" + model.getId() + "` does not exists.");
    }

    Optional<UserResponse> checkUserName = userResponseRepository.findByUserName(model.getUserName());
    if (checkUserName.isPresent() && !checkUserName.get().getId().equals(model.getId())) {
      throw new CustomException("User name already taken.");
    }

    Optional<UserResponse> checkEmail = userResponseRepository.findByEmail(model.getEmail());
    if (checkEmail.isPresent() && !checkEmail.get().getId().equals(model.getId())) {
      throw new CustomException("Email already taken.");
    }

    if (!roleRepository.existsById(model.getRoleId())) {
      throw new CustomException("Role with ID `" + model.getRoleId() + "` does not exists.");
    }

    try {
      userUpdateRepository.save(model);
      return ResponseEntity.status(HttpStatus.CREATED).body("Successfully updated");
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  public ResponseEntity<HttpStatus> delete(Integer id) {
    if (id < MIN_ID || !userRepository.existsById(id)) {
      throw new CustomException("User with ID `" + id + "` does not exists.");
    }
    try {
      userRepository.deleteById(id);
      return ResponseEntity.ok(HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

}


