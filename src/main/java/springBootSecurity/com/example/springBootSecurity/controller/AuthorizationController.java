package springBootSecurity.com.example.springBootSecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springBootSecurity.com.example.springBootSecurity.model.dto.AuthorizationToken;
import springBootSecurity.com.example.springBootSecurity.service.service_api.AuthorizationService;
import springBootSecurity.com.example.springBootSecurity.service.service_api.RoleEndpointsService;

@Validated
@RestController
@RequestMapping(path = "${api.version}/auth")
@Tag(name = "Authorization", description = "Authorization API controller")
@RequiredArgsConstructor
public class AuthorizationController {

  private final AuthorizationService authorizationService;
  private final RoleEndpointsService roleEndpointsService;

  /**
   * Login.
   *
   * @param user     String
   * @param password String
   * @return String
   */
  @PostMapping(path = "/login")
  @Operation(summary = "User login")
  public ResponseEntity<AuthorizationToken> login(
    @Valid @RequestParam("user") String user,
    @RequestParam("password") String password
  ) {
    return authorizationService.login(user, password);
  }

  /**
   * Refresh token.
   *
   * @param refreshToken String
   * @return AuthorizationToken
   */
  @PostMapping(path = "/refresh-token")
  @Operation(summary = "Refresh JWT token")
  public ResponseEntity<AuthorizationToken> refreshToken(
    @Valid @RequestParam("refresh_token") String refreshToken
  ) {
    return authorizationService.refreshToken(refreshToken);
  }

  /**
   * Check user permission.
   *
   * @param roleId     Integer
   * @param endpointId String
   * @return boolean
   */
  @GetMapping(path = "/check-permission")
  @Operation(summary = "Check user permission")
  public boolean checkPermission(
    @NotNull(message = "role_id cannot be empty")
    @RequestParam("role_id")
    Integer roleId,
    @NotNull(message = "endpoint_id cannot be empty")
    @NotEmpty(message = "endpoint_id cannot be empty")
    @RequestParam("endpoint_id")
    String endpointId
  ) {
    return roleEndpointsService.checkPermission(roleId, endpointId);
  }
}
