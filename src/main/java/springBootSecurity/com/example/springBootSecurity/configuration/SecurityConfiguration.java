package springBootSecurity.com.example.springBootSecurity.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springBootSecurity.com.example.springBootSecurity.exception.CustomException;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final AppProperties appProperties;


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    RequestMatcher[] basicAuthEndpoints = Arrays.stream(appProperties.getBasicAuth())
      .map(AntPathRequestMatcher::new)
      .toArray(RequestMatcher[]::new);

    http.authorizeHttpRequests((auths) -> {
          try {
            auths
              .requestMatchers(antMatcher("/api/v1/login/**")).permitAll()
              .requestMatchers(antMatcher("/api/v1/user/**")).permitAll()
              .requestMatchers(basicAuthEndpoints).authenticated()
              .requestMatchers(antMatcher("/**")).permitAll();
          } catch (Exception e) {
            throw new CustomException(e.getMessage());
          }
        }
      )
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .csrf(AbstractHttpConfigurer::disable)
      .cors(withDefaults())
      .httpBasic(withDefaults());

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration.setAllowedMethods(Arrays.asList(appProperties.getSecurityMethods()));
    configuration.setAllowedHeaders(Arrays.asList(appProperties.getSecurityHeaders()));
    configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}

