package pl.coderslab.post;

import lombok.Data;
import pl.coderslab.user.UserDTO;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
}
