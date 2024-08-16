package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.dto.AuthorizationToken;
import springBootSecurity.com.example.springBootSecurity.model.request.UserRefreshTokenUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.UserResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.UserRefreshTokenUpdateRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.UserResponseRepository;
import springBootSecurity.com.example.springBootSecurity.service.service_tools.JwtService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static springBootSecurity.com.example.springBootSecurity.component.AppConst.REFRESH_EXPIRATION_TIME;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

  private final UserResponseRepository userResponseRepository;
  private final UserRefreshTokenUpdateRepository userRefreshTokenUpdateRepository;
  private final JwtService jwtService;

  public ResponseEntity<AuthorizationToken> login(String user, String pass) {
    Optional<UserResponse> result = userResponseRepository.findByUserNameAndStatusIsTrueOrEmailAndStatusIsTrue(user, user);
    if (result.isEmpty()) {
      throw new CustomException("Wrong credentials");
    }
    boolean passwordMatches = BCrypt.checkpw(pass, result.get().getPassword());
    if (!passwordMatches) {
      throw new CustomException("Wrong credentials");
    }
    try {
      AuthorizationToken model = authResult(result.get());
      UserRefreshTokenUpdate mapUser = saveRefreshToken(result.get(), model.getRefreshToken());
      userRefreshTokenUpdateRepository.save(mapUser);
      return ResponseEntity.ok().body(model);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  public ResponseEntity<AuthorizationToken> refreshToken(String refreshToken) {
    Optional<UserResponse> result = userResponseRepository.findByRefreshTokenAndStatusIsTrue(refreshToken);
    if (result.isEmpty()) {
      throw new CustomException("Refresh token not exists.");
    }

    LocalDateTime expire = result.get().getRefreshTokenExpire().toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
    LocalDateTime current = LocalDateTime.now();
    if (current.isAfter(expire)) {
      throw new CustomException("Refresh token expired. Login again.");
    }
    try {
      AuthorizationToken model = authResult(result.get());
      UserRefreshTokenUpdate mapUser = saveRefreshToken(result.get(), model.getRefreshToken());
      userRefreshTokenUpdateRepository.save(mapUser);
      return ResponseEntity.ok().body(model);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  private AuthorizationToken authResult(UserResponse result) {
    String generateRefreshToken = jwtService.generateRefreshToken();
    String generateToken = jwtService.generateToken(result);
    AuthorizationToken model = new AuthorizationToken();
    model.setRefreshToken(generateRefreshToken);
    model.setToken(generateToken);
    return model;
  }

  private UserRefreshTokenUpdate saveRefreshToken(UserResponse result, String token) {
    Date now = new Date();
    Date expire = DateUtils.addHours(now, REFRESH_EXPIRATION_TIME);
    UserRefreshTokenUpdate mapUser = new UserRefreshTokenUpdate();
    mapUser.setId(result.getId());
    mapUser.setRefreshToken(token);
    mapUser.setRefreshTokenExpire(expire);
    return mapUser;
  }
}
