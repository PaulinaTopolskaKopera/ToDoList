package pl.coderslab.post;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO mapToPostDTO(Post post);
}
