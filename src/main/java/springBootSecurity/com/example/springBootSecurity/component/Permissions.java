package springBootSecurity.com.example.springBootSecurity.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;


@Component
@RequiredArgsConstructor
public class Permissions {
  private final AppProperties appProperties;

  /**
   * Check if user super admin.
   *
   * @return boolean
   */
  public boolean ifAdminOrGlobal() {
    return appProperties.getSuperAdmin();
  }
}
