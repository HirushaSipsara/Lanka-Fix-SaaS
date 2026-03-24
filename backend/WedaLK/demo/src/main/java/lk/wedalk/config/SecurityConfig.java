package lk.wedalk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig.java — Spring Security Configuration (DISABLED)
 *
 * <p>Authentication is disabled for development. All endpoints are publicly accessible.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler()))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**", "/api/health").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/quotes").hasRole("WORKER")
            .requestMatchers(HttpMethod.POST, "/api/quotes/*/accept").hasRole("SEEKER")
            .requestMatchers(HttpMethod.DELETE, "/api/quotes/**").hasRole("WORKER")
            .requestMatchers(HttpMethod.GET, "/api/quotes/my").hasRole("WORKER")
            .requestMatchers(HttpMethod.PATCH, "/api/quotes/*/accept", "/api/quotes/*/reject").hasRole("SEEKER")
            .requestMatchers(HttpMethod.GET, "/api/quotes/request/**").hasRole("SEEKER")
            .requestMatchers(HttpMethod.POST, "/api/requests").hasRole("SEEKER")
            .requestMatchers(HttpMethod.GET, "/api/requests/my").hasRole("SEEKER")
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
