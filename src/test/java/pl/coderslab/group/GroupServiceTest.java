package pl.coderslab.group;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.user.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @InjectMocks
    GroupService groupService;
    @Mock
    GroupRepository groupRepository;

    @Test
    void shouldCreateGroup() {
        // given
        User creator = new User(1L, "user1@gmail.com", "user1", "password1", null);
        Group group = new Group(null, "group1", creator, List.of(creator), List.of());
        when(groupRepository.save(group)).thenReturn(group);

        // when
        Group result = groupService.createGroup("group1", creator);

        // then
        assertNotNull(result);
        assertEquals(result.getName(), group.getName());
    }

    @Test
    void shouldFindById() {
        // given
        Group group = new Group(1L, "group1", null, null, null);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        // when
        Group result = groupService.findById(1L);

        // then
        assertNotNull(result);
        assertEquals(result.getName(), group.getName());
    }

    @Test
    void shouldDeleteGroup() {
        // given
        User creator = new User(1L, "user1@gmail.com", "user1", "password1", null);
        Group group = new Group(1L, "group1", creator, List.of(creator), List.of());

        // when
        boolean result = groupService.deleteGroup(creator, group);

        // then
        assertTrue(result);
    }
}