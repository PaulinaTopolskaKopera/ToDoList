package pl.coderslab.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    @Test
    void shouldFindById() {
        // given
        User user1 = new User(1L, "user1@gmail.com", "user1", "password1", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        // when
        User result = userService.findById(1L);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldFindByUsername() {
        // given
        User user1 = new User(1L, "user1@gmail.com", "user1", "password1", null);
        when(userRepository.findByUsername("user1")).thenReturn(user1);

        // when
        User result = userService.findByUsername("user1");

        // then
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    void shouldCreateUser() {
        // given
        User user1 = new User(null, "user1@gmail.com", "user1", "password1", new ArrayList<>());
        when(userRepository.save(user1)).thenReturn(user1);

        // when
        User result = userService.createUser("user1@gmail.com", "user1", "password1");

        // then
        assertNotNull(result);
        assertEquals(result.getUsername(), user1.getUsername());
    }
}