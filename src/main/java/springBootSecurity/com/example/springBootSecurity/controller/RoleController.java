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
import springBootSecurity.com.example.springBootSecurity.model.request.Role;
import springBootSecurity.com.example.springBootSecurity.model.request.RoleUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleResponse;
import springBootSecurity.com.example.springBootSecurity.service.service_api.RoleService;

@Validated
@RestController
@RequestMapping(path = "${api.version}/role")
@Tag(name = "Role", description = "Role API controller")
@RequiredArgsConstructor
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

  private final RoleService roleService;

  /**
   * Create.
   *
   * @param model Role
   * @return RoleResponse
   */
  @PostMapping(path = "/create")
  @Operation(summary = "Create new role", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<RoleResponse> create(@Valid @RequestBody Role model) {
    return roleService.create(model);
  }

  /**
   * Update.
   *
   * @param model RoleUpdate
   * @return String
   */
  @PutMapping(path = "/update")
  @Operation(summary = "Update existing role", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> update(@Valid @RequestBody RoleUpdate model) {
    return roleService.update(model);
  }

  /**
   * Delete.
   *
   * @param id Integer
   * @return HttpStatus
   */
  @DeleteMapping(path = "/delete-by-id")
  @Operation(summary = "Delete one role by ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<HttpStatus> delete(@Valid @RequestParam("id") Integer id) {
    return roleService.delete(id);
  }
}
