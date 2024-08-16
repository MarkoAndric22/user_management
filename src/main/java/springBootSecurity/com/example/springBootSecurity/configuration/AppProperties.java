package springBootSecurity.com.example.springBootSecurity.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Configuration
public class AppProperties {

  /**
   * Endpoints with basic auth.
   */
  public final String[] basicAuth = {
    "/api/v1/login/**"

  };

  /**
   * Security Allowed Methods.
   */
  public final String[] securityMethods = {
    "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "OPTIONS"
  };

  /**
   * Security Allowed Headers.
   */
  public final String[] securityHeaders = {
    "authorization", "content-type", "x-auth-token", "permissions"
  };

  public final String[] excludedRoutes = {
    "/api/v1/login/**",
    "/api/v1/user/**"
  };

  public final String[] excludedPackets = {
    "/api/v1/login/**",
    "/api/v1/user/**"
  };

  @Value("${jwt.secret}")
  private String jwtSecret;

  /**
   * Global endpoint.
   */
  private Boolean endpointGlobal = false;

  /**
   * Super admin.
   */
  private Boolean superAdmin = false;

  /**
   * DOMAIN
   */
  @Value("${domain.name}")
  private String domainName;

  /**
   * IMAGE
   */
  @Value("${upload.image.path}")
  private String uploadImagePath;

  @Value("${type.company.user}")
  private String userType;

  @Value("${web.user}")
  private String userWebPath;

  @Value("${role.user}")
  private int userRole;

  @Value("${type.company.admin}")
  private String adminType;

  @Value("${web.admin}")
  private String adminWebPath;

  @Value("${role.admin}")
  private int adminRole;

  @Value("${spring.security.user.name}")
  private String globalUser;

  @Value("${spring.security.user.password}")
  private String globalPassword;

  @Value("${spring.application.name}")
  private String msApplicationName;


}
