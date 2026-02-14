package lk.wedalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * WedaLkApplication — Main Spring Boot Application Entry Point.
 * Bootstraps the embedded Tomcat server and initializes the Spring context.
 *
 * NOTE: DataSource and JPA auto-configuration are excluded so the app
 * can start without a database for health check testing.
 * Remove the 'exclude' parameter once PostgreSQL is configured.
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class WedaLkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WedaLkApplication.class, args);
    }
}
