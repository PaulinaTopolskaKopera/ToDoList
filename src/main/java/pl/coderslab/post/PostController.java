package pl.coderslab.post;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.auth.Visitor;
import pl.coderslab.group.Group;
import pl.coderslab.group.GroupService;
import pl.coderslab.user.User;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    private final Visitor visitor;
    private final GroupService groupService;
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper, GroupService groupService, Visitor visitor) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.groupService = groupService;
        this.visitor = visitor;
    }

    @GetMapping("/{groupId}/posts")
    public List<PostDTO> getAllPosts(@PathVariable Long groupId, HttpServletResponse response) {
        Group group = groupService.findById(groupId);

        if (group == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        boolean isMember = group.getMembers().stream()
                .map(User::getId)
                .toList().contains(visitor.getUser().getId());

        if (!visitor.isLoggedIn() || !isMember) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        return group.getPosts().stream().map(postMapper::mapToPostDTO).toList();
    }

    @PostMapping("/{groupId}/create")
    public PostDTO addPost(@PathVariable Long groupId,
                        @Valid @RequestBody PostCreationInfo info,
                        HttpServletResponse response) {

        Group group = groupService.findById(groupId);

        if (group == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        boolean isMember = group.getMembers().stream()
                .map(User::getId)
                .toList().contains(visitor.getUser().getId());

        if (!visitor.isLoggedIn() || !isMember) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Post createdPost = postService.createPost(group, visitor.getUser(), info.getTitle(), info.getContent());
        groupService.addGroupPost(group, createdPost);

        return postMapper.mapToPostDTO(createdPost);
    }
}
