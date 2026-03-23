package oma.kirja.kauppa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import oma.kirja.kauppa.domain.User;
import oma.kirja.kauppa.domain.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        // Create and save a user
        User user = new User("testuser", "password123", "test@example.com", "USER");
        User savedUser = userRepository.save(user);

        // Verify the user was saved
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getRole()).isEqualTo("USER");
    }

    @Test
    public void testDeleteUser() {
        // Create and save a user
        User user = new User("testuser", "password123", "test@example.com", "USER");
        User savedUser = userRepository.save(user);

        // Delete the user
        userRepository.deleteById(savedUser.getId());

        // Verify the user was deleted
        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    public void testFindByUsername() {
        // Create and save users
        User user1 = new User("user1", "pass1", "user1@example.com", "USER");
        User user2 = new User("user2", "pass2", "user2@example.com", "ADMIN");
        userRepository.save(user1);
        userRepository.save(user2);

        // Find user by username
        Optional<User> foundUser = userRepository.findByUsername("user1");

        // Verify results
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("user1");
        assertThat(foundUser.get().getEmail()).isEqualTo("user1@example.com");
    }
}