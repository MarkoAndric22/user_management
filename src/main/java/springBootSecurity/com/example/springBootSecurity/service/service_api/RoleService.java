package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.request.Role;
import springBootSecurity.com.example.springBootSecurity.model.request.RoleUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.RoleResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.RoleRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.RoleUpdateRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.RoleResponseRepository;

import java.util.Optional;

import static springBootSecurity.com.example.springBootSecurity.component.AppConst.MIN_ID;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final RoleUpdateRepository roleUpdateRepository;
  private final RoleResponseRepository roleResponseRepository;

  /**
   * Create role
   *
   * @param model
   * @return
   */
  public ResponseEntity<RoleResponse> create(Role model) {
    Optional<RoleResponse> check = roleResponseRepository.findByName(model.getName());
    if (check.isPresent()) {
      throw new CustomException("Role with name `" + model.getName() + "` already exists.");
    }
    try {
      Role save = roleRepository.save(model);
      Optional<RoleResponse> result = roleResponseRepository.findById(save.getId());
      return result
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
        .orElseGet(() -> ResponseEntity.noContent().build());
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Update role
   *
   * @param model
   * @return
   */
  public ResponseEntity<String> update(RoleUpdate model) {
    if (model.getId() < MIN_ID || !roleResponseRepository.existsById(model.getId())) {
      throw new CustomException("Role with ID `" + model.getId() + "` not exists.");
    }
    try {
      roleUpdateRepository.save(model);
      return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Delete role
   *
   * @param id
   * @return
   */
  public ResponseEntity<HttpStatus> delete(Integer id) {
    if (id < MIN_ID || !roleResponseRepository.existsById(id)) {
      throw new CustomException("Role with ID `" + id + "` not exists.");
    }
    try {
      roleRepository.deleteById(id);
      return ResponseEntity.ok(HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }
}
