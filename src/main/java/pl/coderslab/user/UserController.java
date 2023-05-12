package pl.coderslab.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.auth.Visitor;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final Visitor visitor;

    public UserController(UserMapper userMapper, UserService userService, Visitor visitor) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.visitor = visitor;
    }

    @GetMapping("/{username}")
    public UserDTO getByUsername(@PathVariable String username, HttpServletResponse response) {
        User user = userService.findByUsername(username);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return userMapper.mapToUserDTO(user);
    }

    @GetMapping("/me")
    public UserDTO getMe(HttpServletResponse response) {
        if (!visitor.isLoggedIn()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return userMapper.mapToUserDTO(visitor.getUser());
    }

   @PostMapping("/me/update")
    public void updateUser(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                          HttpServletResponse response) {

        if (!visitor.isLoggedIn()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        userService.updateUser(visitor.getUser(), email, BCrypt.hashpw(password,BCrypt.gensalt()));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
