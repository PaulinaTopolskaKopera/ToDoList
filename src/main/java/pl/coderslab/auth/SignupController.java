package pl.coderslab.auth;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.user.User;
import pl.coderslab.user.UserDTO;
import pl.coderslab.user.UserMapper;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class SignupController {

    private final UserService userService;
    private final UserMapper userMapper;

    public SignupController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public UserDTO register(@Valid @RequestBody UserSignupInfo info,
                            HttpServletResponse response) {

        User createdUser = userService.createUser(info.getEmail(), info.getUsername(), BCrypt.hashpw(info.getPassword(), BCrypt.gensalt()));

        if (createdUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        return userMapper.mapToUserDTO(createdUser);
    }

}
