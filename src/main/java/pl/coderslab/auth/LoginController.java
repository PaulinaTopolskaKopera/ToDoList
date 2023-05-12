package pl.coderslab.auth;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class LoginController {

    private final UserService userService;
    private final Visitor visitor;

    public LoginController(UserService userService, Visitor currentVisitor) {
        this.userService = userService;
        this.visitor = currentVisitor;
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response,
                      @Valid @RequestBody UserLoginInfo info) {

        if (visitor.isLoggedIn()) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        User user = userService.findByUsername(info.getUsername());
        if (user == null || !BCrypt.checkpw(info.getPassword(), user.getPassword())) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        visitor.setUser(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        if (visitor.isLoggedIn()) {
            visitor.setUser(null);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
