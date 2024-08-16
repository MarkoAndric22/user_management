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
import springBootSecurity.com.example.springBootSecurity.model.request.RoleMenu;
import springBootSecurity.com.example.springBootSecurity.model.request.RoleMenuUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleMenuResponse;
import springBootSecurity.com.example.springBootSecurity.service.service_api.RoleMenuService;

@Validated
@RestController
@RequestMapping(path = "${api.version}/role/menu")
@Tag(name = "Role Menu", description = "Role Menu API controller")
@RequiredArgsConstructor
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class RoleMenuController {

  private static RoleMenuService roleMenuService;

  /**
   * Create.
   *
   * @param model Menu
   * @return RoleMenuResponse
   */
  @PostMapping(path = "/create")
  @Operation(summary = "Create new Role Menu", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<RoleMenuResponse> create(@Valid @RequestBody RoleMenu model) {
    return roleMenuService.create(model);
  }

  /**
   * Update.
   *
   * @param model RoleMenuUpdate
   * @return String
   */
  @PutMapping(path = "/update")
  @Operation(summary = "Update existing Role Menu", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> update(@Valid @RequestBody RoleMenuUpdate model) {
    return roleMenuService.update(model);
  }

  /**
   * Delete.
   *
   * @param id Integer
   * @return HttpStatus
   */
  @DeleteMapping(path = "/delete-by-id")
  @Operation(summary = "Delete one Role Menu by ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<HttpStatus> delete(@Valid @RequestParam("id") Integer id) {
    return roleMenuService.delete(id);
  }
}
