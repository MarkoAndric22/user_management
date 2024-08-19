package springBootSecurity.com.example.springBootSecurity.service.service_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;
import springBootSecurity.com.example.springBootSecurity.model.request.Menu;
import springBootSecurity.com.example.springBootSecurity.model.request.MenuUpdate;
import springBootSecurity.com.example.springBootSecurity.model.response.MenuResponse;
import springBootSecurity.com.example.springBootSecurity.repository.request.MenuRepository;
import springBootSecurity.com.example.springBootSecurity.repository.request.MenuUpdateRepository;
import springBootSecurity.com.example.springBootSecurity.repository.response.MenuResponseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuResponseRepository menuResponseRepository;
  private final MenuRepository menuRepository;
  private final MenuUpdateRepository menuUpdateRepository;

  /**
   * Create menu
   *
   * @param model
   * @return
   */
  public ResponseEntity<MenuResponse> create(Menu model) {
    Optional<MenuResponse> check = menuResponseRepository.findByMenuNumber(model.getMenuNumber());
    if (check.isPresent()) {
      throw new CustomException("Menu with number `" + model.getMenuNumber() + "` already exists.");
    }
    try {
      Menu save = menuRepository.save(model);
      Optional<MenuResponse> result = menuResponseRepository.findById(save.getId());
      return result
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
        .orElseGet(() -> ResponseEntity.noContent().build());
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Update menu
   *
   * @param model
   * @return
   */
  public ResponseEntity<String> update(MenuUpdate model) {
    if (!menuResponseRepository.existsById(model.getId())) {
      throw new CustomException("Menu with ID `" + model.getId() + "` not exists.");
    }
    try {
      menuUpdateRepository.save(model);
      return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }

  /**
   * Delete menu
   *
   * @param id
   * @return
   */
  public ResponseEntity<HttpStatus> delete(Integer id) {
    if (!menuResponseRepository.existsById(id)) {
      throw new CustomException("Menu with ID `" + id + "` not exists.");
    }
    try {
      menuRepository.deleteById(id);
      return ResponseEntity.ok(HttpStatus.OK);
    } catch (Exception e) {
      throw new CustomException(e.getMessage());
    }
  }
}
