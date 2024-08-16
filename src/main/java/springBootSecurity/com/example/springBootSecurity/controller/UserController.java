package springBootSecurity.com.example.springBootSecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springBootSecurity.com.example.springBootSecurity.model.request.User;
import springBootSecurity.com.example.springBootSecurity.model.request.UserUpdate;
import springBootSecurity.com.example.springBootSecurity.service.service_api.UserService;


@Validated
@RestController
@RequestMapping(path = "${api.version}/user")
@Tag(name = "User", description = "User API controller")
@RequiredArgsConstructor
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class UserController {

  private final UserService userService;

  /**
   * Create.
   *
   * @param model User
   * @return String
   */
  @PostMapping(path = "/create")
  @Operation(summary = "Create new user", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> create(@Valid @RequestBody User model) {
    return userService.create(model);
  }

  /**
   * Update.
   *
   * @param model UserUpdate
   * @return String
   */
  @PutMapping(path = "/update")
  @Operation(summary = "Update existing user", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> update(@Valid @RequestBody UserUpdate model) {
    return userService.update(model);
  }

  /**
   * Delete.
   *
   * @param id Integer
   * @return HttpStatus
   */
  @DeleteMapping(path = "/delete-by-id")
  @Operation(summary = "Read one user by ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<HttpStatus> delete(@Valid @RequestParam("id") Integer id) {
    return userService.delete(id);
  }

}
