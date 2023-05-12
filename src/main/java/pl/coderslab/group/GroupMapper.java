package pl.coderslab.group;

import org.mapstruct.Mapper;
import pl.coderslab.user.UserMapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface GroupMapper {
    GroupDTO mapToGroupDTO(Group group);
}