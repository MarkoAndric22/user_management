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
import springBootSecurity.com.example.springBootSecurity.model.dto.RoleEndpointsDto;
import springBootSecurity.com.example.springBootSecurity.service.service_api.RoleEndpointsService;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "${api.version}/role-endpoints")
@Tag(name = "Role Endpoints", description = "Role Endpoints API controller")
@RequiredArgsConstructor
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class RoleEndpointsController {

  private final RoleEndpointsService roleEndpointsService;

  /**
   * Create.
   *
   * @param models RoleEndpointsDto
   * @return String
   */
  @PostMapping(path = "/create")
  @Operation(summary = "Create new role endpoints", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> create(@Valid @RequestBody RoleEndpointsDto models) {
    return roleEndpointsService.create(models);
  }

  /**
   * Delete.
   *
   * @param ids List Integer
   * @return HttpStatus
   */
  @DeleteMapping(path = "/delete-by-ids")
  @Operation(summary = "Delete role endpoints by IDs", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<HttpStatus> delete(@Valid @RequestParam("id") List<Integer> ids) {
    return roleEndpointsService.delete(ids);
  }


}
