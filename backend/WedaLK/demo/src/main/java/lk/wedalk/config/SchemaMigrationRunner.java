package lk.wedalk.config;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * SchemaMigrationRunner — idempotent startup DB migrations.
 *
 * <p>Drops stale Postgres CHECK constraints on enum-backed columns (e.g.
 * {@code service_requests_status_check}) that were created by an older version
 * of Hibernate when the enum had fewer values. With {@code ddl-auto=update},
 * Hibernate does not update existing CHECK constraints, so inserts with new
 * enum values (such as {@code PENDING_PAYMENT}) fail until the constraint is
 * dropped. Java's {@code @Enumerated(EnumType.STRING)} still enforces valid
 * values before the DB ever sees them.
 *
 * <p>Safe to run on every startup — uses {@code DROP CONSTRAINT IF EXISTS}.
 */
@Configuration
public class SchemaMigrationRunner {

    /** (table, constraint) pairs to drop if they exist. */
    private static final List<String[]> STALE_CONSTRAINTS = List.of(
            new String[] {"service_requests", "service_requests_status_check"},
            new String[] {"service_requests", "service_requests_urgency_check"},
            new String[] {"service_requests", "service_requests_category_check"},
            new String[] {"quotations", "quotations_status_check"},
            new String[] {"users", "users_role_check"},
            new String[] {"users", "users_verification_status_check"},
            new String[] {"disputes", "disputes_status_check"},
            new String[] {"verification_requests", "verification_requests_status_check"}
    );

    @Bean
    @Order(0) // run before DataSeeder
    CommandLineRunner dropStaleCheckConstraints(DataSource dataSource) {
        return args -> {
            JdbcTemplate jdbc = new JdbcTemplate(dataSource);
            for (String[] tc : STALE_CONSTRAINTS) {
                String sql = "ALTER TABLE IF EXISTS " + tc[0]
                        + " DROP CONSTRAINT IF EXISTS " + tc[1];
                try {
                    jdbc.execute(sql);
                } catch (Exception ex) {
                    System.err.println("[SchemaMigration] skipped: " + sql
                            + " -> " + ex.getMessage());
                }
            }
            System.out.println("[SchemaMigration] Stale CHECK constraints dropped (if any).");
        };
    }
}
