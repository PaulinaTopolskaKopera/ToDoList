package pl.coderslab.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginInfo {
    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    @Size(min = 8, max = 30)
    private String password;
}