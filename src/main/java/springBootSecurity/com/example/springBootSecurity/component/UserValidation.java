package springBootSecurity.com.example.springBootSecurity.component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;
import springBootSecurity.com.example.springBootSecurity.service.service_tools.JwtService;

@Component
@RequiredArgsConstructor
public class UserValidation {

  private final JwtService jwtService;
  private final AppProperties appProperties;

  public boolean validate(HttpServletRequest request, String endpoint) {
    Integer roleId = jwtService.getRoleId(request);
    if (roleId.equals(appProperties.getUserRole()) && endpoint.contains(appProperties.getUserWebPath())) {
      return true;
    } else return roleId.equals(appProperties.getAdminRole()) && endpoint.contains(appProperties.getAdminWebPath());
  }
}
