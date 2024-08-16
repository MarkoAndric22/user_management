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
import springBootSecurity.com.example.springBootSecurity.model.request.Menu;
import springBootSecurity.com.example.springBootSecurity.model.request.MenuUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.MenuResponse;
import springBootSecurity.com.example.springBootSecurity.service.service_api.MenuService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
@RestController
@RequestMapping(path = "${api.version}/menu")
@Tag(name = "Menu", description = "Menu API controller")
@RequiredArgsConstructor
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@SecurityRequirement(name = "bearerAuth")
public class MenuController {

  private final MenuService menuService;

  /**
   * Create.
   *
   * @param model Menu
   * @return MenuResponse
   */
  @PostMapping(path = "/create")
  @Operation(summary = "Create new Menu", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<MenuResponse> create(@Valid @RequestBody Menu model) {
    return menuService.create(model);
  }

  /**
   * Update.
   *
   * @param model MenuUpdate
   * @return String
   */
  @PutMapping(path = "/update")
  @Operation(summary = "Update existing Menu", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<String> update(@Valid @RequestBody MenuUpdate model) {
    return menuService.update(model);
  }

  /**
   * Delete.
   *
   * @param id Integer
   * @return HttpStatus
   */
  @DeleteMapping(path = "/delete-by-id")
  @Operation(summary = "Delete one Menu by ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<HttpStatus> delete(@Valid @RequestParam("id") Integer id) {
    return menuService.delete(id);
  }
}
