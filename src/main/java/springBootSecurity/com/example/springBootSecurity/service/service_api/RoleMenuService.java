package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.request.RoleMenu;
import springBootSecurity.com.example.springBootSecurity.model.request.RoleMenuUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleMenuResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.RoleMenuRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.RoleMenuUpdateRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.RoleMenuResponseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleMenuService {

  private final RoleMenuRepository roleMenuRepository;
  private final RoleMenuUpdateRepository roleMenuUpdateRepository;
  private final RoleMenuResponseRepository roleMenuResponseRepository;

  public ResponseEntity<RoleMenuResponse> create(RoleMenu model) {
    Optional<RoleMenuResponse> check = roleMenuResponseRepository.findByRoleIdAndMenuId(model.getRoleId(), model.getMenuId());
    if (check.isPresent()) {
      throw new CustomException("Combination already exists.");
    }
    try {
      RoleMenu save = roleMenuRepository.save(model);
      Optional<RoleMenuResponse> result = roleMenuResponseRepository.findById(save.getId());
      return result
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
        .orElseGet(() -> ResponseEntity.noContent().build());
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  public ResponseEntity<String> update(RoleMenuUpdate model) {
    if (!roleMenuResponseRepository.existsById(model.getId())) {
      throw new CustomException("Role Menu with ID `" + model.getId() + "` not exists.");
    }
    try {
      roleMenuUpdateRepository.save(model);
      return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  public ResponseEntity<HttpStatus> delete(Integer id) {
    if (!roleMenuResponseRepository.existsById(id)) {
      throw new CustomException("Role Menu with ID `" + id + "` not exists.");
    }
    try {
      roleMenuRepository.deleteById(id);
      return ResponseEntity.ok(HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }
}
