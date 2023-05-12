package pl.coderslab.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostService postService;
    @Mock
    PostRepository postRepository;

    @Test
    void shouldFindById() {
        // given
        Post post = new Post(1L, null, "title1", "content1", null);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // when
        Post result = postService.findById(1L);

        // then
        assertNotNull(result);
        assertEquals(result.getTitle(), post.getTitle());
    }

    @Test
    void shouldGetAll() {
        // given
        Post post1 = new Post(1L, null, "title1", "content1", null);
        Post post2 = new Post(2L, null, "title2", "content2", null);
        when(postRepository.findAll()).thenReturn(List.of(post1, post2));

        // when
        List<Post> result = postService.getAll();

        // then
        assertNotNull(result);
        assertEquals(result.size(), 2);
    }

    @Test
    void shouldCreatePost() {
        // given
        Post post1 = new Post(null, null, "title1", "content1", null);
        when(postRepository.save(post1)).thenReturn(post1);

        // when
        Post result = postService.createPost(null, null, "title1", "content1");

        // then
        assertNotNull(result);
        assertEquals(result.getTitle(), post1.getTitle());
    }
}