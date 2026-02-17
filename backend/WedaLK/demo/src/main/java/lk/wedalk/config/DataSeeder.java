package lk.wedalk.config;

import lk.wedalk.users.model.Role;
import lk.wedalk.users.model.User;
import lk.wedalk.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = User.builder()
                        .fullName("Test Worker")
                        .email("test@worker.com")
                        .password("password") // In real app, hash this
                        .district("Colombo")
                        .role(Role.WORKER)
                        .build();

                userRepository.save(user);
                System.out.println("Seeded default user with ID: " + user.getId());
            }
        };
    }
}
