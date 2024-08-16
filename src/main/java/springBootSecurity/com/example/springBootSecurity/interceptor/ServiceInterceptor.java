package springBootSecurity.com.example.springBootSecurity.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import springBootSecurity.com.example.springBootSecurity.component.UserValidation;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;
import springBootSecurity.com.example.springBootSecurity.service.service_tools.MappingService;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ServiceInterceptor implements HandlerInterceptor {

  private final AppProperties appProperties;
  private final MappingService mappingService;
  private final UserValidation userValidation;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    String endpoint = mappingService.endpoint(request);

    if (!endpoint.equals("/**") && !Arrays.asList(appProperties.getExcludedRoutes()).contains(endpoint)) {
      boolean validation = userValidation.validate(request, endpoint);
      if (!validation) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("You have no privileges");
        return false;
      }
      return true;
    }
    return true;
  }
}
