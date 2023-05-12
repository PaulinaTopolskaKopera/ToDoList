package pl.coderslab.group;

import org.springframework.stereotype.Service;
import pl.coderslab.post.Post;
import pl.coderslab.user.User;

import java.util.ArrayList;

@Service
public class GroupService {

    GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group createGroup(String name, User creator) {
        Group group = new Group();
        group.setName(name);
        group.setCreator(creator);

        group.setMembers(new ArrayList<>());
        group.setPosts(new ArrayList<>());

        group.getMembers().add(creator);

        groupRepository.save(group);
        return group;
    }

    public boolean updateGroup(User user, Group group, String name) {
        if (!group.getCreator().getId().equals(user.getId())) {
            return false;
        }

        group.setName(name);
        groupRepository.save(group);
        return true;
    }

    public void addGroupPost(Group group, Post post) {
        group.getPosts().add(post);

        groupRepository.save(group);
    }


    public Group findById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public boolean deleteGroup(User deleter, Group group) {
        if (!group.getCreator().getId().equals(deleter.getId())) {
            return false;
        }

        groupRepository.delete(group);
        return true;
    }

}
