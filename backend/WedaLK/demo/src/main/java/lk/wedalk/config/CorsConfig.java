package lk.wedalk.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfig — Enables CORS so the React frontend (port 3000) can talk to the Spring Boot backend.
 *
 * <p>Two beans are required:
 * <ul>
 *   <li>{@code corsConfigurer} — used by Spring MVC for @CrossOrigin and handler-level CORS.</li>
 *   <li>{@code corsConfigurationSource} — used by Spring Security (.cors(Customizer.withDefaults()))
 *       to allow CORS preflight OPTIONS requests through the security filter chain without
 *       requiring authentication.</li>
 * </ul>
 */
@Configuration
public class CorsConfig {

  @Value("${app.cors.allowed-origins}")
  private List<String> allowedOrigins;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/api/**")
            .allowedOrigins(allowedOrigins.toArray(new String[0]))
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
      }
    };
  }

  /**
   * Standalone CorsConfigurationSource consumed by Spring Security's CORS filter.
   * Without this, Spring Security would block preflight OPTIONS requests before they
   * reach the MVC dispatcher, causing the browser to report 401 on the actual request.
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(allowedOrigins);
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", config);
    return source;
  }
}
