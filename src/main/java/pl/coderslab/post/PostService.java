package pl.coderslab.post;

import org.springframework.stereotype.Service;
import pl.coderslab.group.Group;
import pl.coderslab.user.User;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post createPost(Group group, User author, String title, String content) {
        Post post = new Post();
        post.setGroup(group);
        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);

        postRepository.save(post);
        return post;
    }
}
