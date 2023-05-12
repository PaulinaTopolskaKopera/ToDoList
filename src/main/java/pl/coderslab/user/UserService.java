package pl.coderslab.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(String email, String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null) return null;

        user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        user.setGroups(new ArrayList<>());

        userRepository.save(user);
        return user;
    }

    public void updateUser(User user, String email, String password) {
        if (user == null) return;

        if (email != null) user.setEmail(email);
        if (password != null) user.setPassword(password);

        userRepository.save(user);
    }

}
