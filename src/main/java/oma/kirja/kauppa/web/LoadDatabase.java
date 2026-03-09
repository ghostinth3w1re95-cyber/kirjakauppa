package oma.kirja.kauppa.web;

import oma.kirja.kauppa.domain.User;
import oma.kirja.kauppa.domain.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if(userRepository.count() == 0) {
                userRepository.save(new User(
                        "admin",
                        encoder.encode("adminpass"),
                        "admin@example.com",
                        "ADMIN"
                ));

                userRepository.save(new User(
                        "user",
                        encoder.encode("userpass"),
                        "user@example.com",
                        "USER"
                ));
            }
        };
    }
}