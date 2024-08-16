package springBootSecurity.com.example.springBootSecurity.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springBootSecurity.com.example.springBootSecurity.configuration.AppProperties;


@Configuration
@RequiredArgsConstructor
public class ServiceInterceptorAppConfig implements WebMvcConfigurer {
  private final ServiceInterceptor serviceInterceptor;
  private final AppProperties appProperties;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
      .addInterceptor(serviceInterceptor)
      .excludePathPatterns(appProperties.getExcludedRoutes());
  }
}
