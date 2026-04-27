package lk.wedalk.config;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Startup guardrails for security-sensitive configuration.
 *
 * <p>This validator runs in warning mode: it does not block startup, but emits
 * clear warnings for unsafe or missing configuration so teams can harden before
 * production deployment.
 */
@Component
public class SecurityConfigValidator implements ApplicationRunner {

  private static final Logger log = LoggerFactory.getLogger(SecurityConfigValidator.class);

  private static final String LEGACY_JWT_DEFAULT =
      "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

  private final Environment environment;

  @Value("${app.jwt.secret:}")
  private String jwtSecret;

  @Value("${app.admin.password:}")
  private String adminPassword;

  @Value("${app.ai.gemini.api-key:}")
  private String geminiApiKey;

  @Value("${spring.datasource.username:}")
  private String dbUsername;

  @Value("${spring.datasource.password:}")
  private String dbPassword;

  public SecurityConfigValidator(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void run(ApplicationArguments args) {
    List<String> warnings = new ArrayList<>();

    if (isBlank(jwtSecret)) {
      warnings.add("JWT secret is blank. Authentication tokens will be insecure or fail unexpectedly.");
    } else if (LEGACY_JWT_DEFAULT.equals(jwtSecret)) {
      warnings.add("JWT secret still uses the legacy default value. Set JWT_SECRET from environment.");
    } else if (jwtSecret.length() < 32) {
      warnings.add("JWT secret length is weak (< 32 chars). Use a long, random secret.");
    }

    if (isBlank(adminPassword)) {
      warnings.add("Seed admin password is blank. Set app.admin.password or ADMIN_PASSWORD in environment.");
    } else if ("admin123".equals(adminPassword)) {
      warnings.add("Seed admin password is still 'admin123'. Change it before any shared/test deployment.");
    }

    if (isBlank(geminiApiKey)) {
      warnings.add("GEMINI_API_KEY is missing. AI Assist will be unavailable.");
    }

    if (isBlank(dbUsername) || isBlank(dbPassword)) {
      warnings.add("Database credentials are missing. Set spring.datasource.username/password via environment.");
    } else if ("postgres".equalsIgnoreCase(dbUsername) && "123".equals(dbPassword)) {
      warnings.add("Database credentials appear to be local defaults (postgres / 123). Rotate before deployment.");
    }

    String[] activeProfiles = environment.getActiveProfiles();
    String profileText = activeProfiles.length == 0 ? "default" : String.join(", ", activeProfiles);

    if (warnings.isEmpty()) {
      log.info("Security config validation completed (warn mode). Active profiles: {}", profileText);
      return;
    }

    log.warn("Security config validation found {} issue(s) (warn mode). Active profiles: {}",
        warnings.size(), profileText);
    for (String warning : warnings) {
      log.warn("SECURITY WARNING: {}", warning);
    }
  }

  private boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }
}

