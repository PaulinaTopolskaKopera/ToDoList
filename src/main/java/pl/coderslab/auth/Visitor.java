package pl.coderslab.auth;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.coderslab.user.User;

@Data
@Component
@SessionScope
public class Visitor {

    private User user;

    public boolean isLoggedIn() {
        return user != null;
    }
}
