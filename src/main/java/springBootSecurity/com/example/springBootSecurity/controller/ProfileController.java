package springBootSecurity.com.example.springBootSecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springBootSecurity.com.example.springBootSecurity.model.dto.ProfileDto;
import springBootSecurity.com.example.springBootSecurity.model.response.UserResponse;
import springBootSecurity.com.example.springBootSecurity.service.service_api.ProfileService;

@Validated
@RestController
@RequestMapping(path = "${api.version}/profile")
@Tag(name = "Profile", description = "Profile API controller")
@RequiredArgsConstructor
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

  private final ProfileService profileService;

  /**
   * Read by ID.
   *
   * @param id Integer
   * @return UserResponse
   */
  @GetMapping(path = "/read-by-id")
  @Operation(summary = "Read one user by ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<UserResponse> readById(@Valid @RequestParam("id") Integer id) {
    return profileService.readById(id);
  }

  /**
   * Update.
   *
   * @param model ProfileDto
   * @return String
   */
  @PutMapping(path = "/update")
  @Operation(summary = "Update existing user", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> update(@Valid @RequestBody ProfileDto model) {
    return profileService.update(model);
  }

  /**
   * Update password.
   *
   * @param id       Integer
   * @param password String
   * @return String
   */
  @PutMapping(path = "/change-password")
  @Operation(summary = "Change user password", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> updatePass(
    @NotNull(message = "id cannot be empty.")
    @RequestParam("id")
    Integer id,
    @NotNull(message = "password cannot be empty.")
    @NotEmpty(message = "password cannot be empty.")
    @RequestParam("password")
    String password
  ) {
    return profileService.updatePass(id, password);
  }

  /**
   * Update image.
   *
   * @param id   Integer
   * @param file MultipartFile
   * @return String
   */
  @PutMapping(path = "/change-image")
  @Operation(summary = "Change user image", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> updateImage(
    @NotNull(message = "id cannot be empty.")
    @RequestParam("id")
    Integer id,
    @NotNull(message = "file cannot be empty.")
    @RequestParam("file") MultipartFile file
  ) {
    return profileService.updateImage(id, file);
  }

  /**
   * Verify phone.
   *
   * @param id  Integer
   * @param otp String
   * @return String
   */
  @PostMapping(path = "/verify-phone")
  @Operation(summary = "Verify user phone", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> verifyPhone(
    @NotNull(message = "id cannot be empty.")
    @RequestParam("id")
    Integer id,
    @NotNull(message = "otp cannot be empty.")
    @RequestParam("otp") String otp
  ) {
    return profileService.verifyPhone(id, otp);
  }
}
