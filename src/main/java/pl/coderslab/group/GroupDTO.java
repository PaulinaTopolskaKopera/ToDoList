package pl.coderslab.group;

import lombok.Data;
import pl.coderslab.user.UserDTO;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private UserDTO creator;

//    private List<User> members;
//    private List<Post> posts;
}
