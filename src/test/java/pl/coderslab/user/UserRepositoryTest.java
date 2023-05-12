package pl.coderslab.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;


    @BeforeAll
    public void setup() {
        // given
        user = new User();
        user.setUsername("username1");
        user.setPassword("password1");
        user.setEmail("email1@gmail.com");

        userRepository.save(user);
    }

    @AfterAll
    public void cleanup() {
        userRepository.delete(user);
    }

    @Test
    public void shouldFindUserByUsername() throws Exception {
        // when
        User result = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void shouldNotFindUserByUsername() throws Exception {
        // when
        User result = userRepository.findByUsername("nonexistent");

        // then
        assertNull(result);
    }

}