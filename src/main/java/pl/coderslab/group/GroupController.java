package pl.coderslab.group;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.auth.Visitor;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final Visitor visitor;
    private final GroupService groupService;
    private final GroupMapper groupMapper;
    public GroupController(GroupService groupService, GroupMapper groupMapper, Visitor visitor) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
        this.visitor = visitor;
    }

    @GetMapping("/{id}")
    public GroupDTO getGroup(@PathVariable Long id, HttpServletResponse response) {
        Group group = groupService.findById(id);

        if (group == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return groupMapper.mapToGroupDTO(group);
    }

    @PostMapping("/create")
    public GroupDTO createGroup(@RequestParam("name") String name, HttpServletResponse response) {
        if (!visitor.isLoggedIn()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        return groupMapper.mapToGroupDTO(groupService.createGroup(name, visitor.getUser()));
    }

    @PostMapping("/{id}/update")
    public void updateGroup(@PathVariable Long id,
                            @RequestParam("name") String name,
                            HttpServletResponse response) {

        Group group = groupService.findById(id);
        if (group == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!visitor.isLoggedIn() || !group.getCreator().getId().equals(visitor.getUser().getId())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        groupService.updateGroup(visitor.getUser(), group, name);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/{id}/delete")
    public void deleteGroup(@PathVariable Long id, HttpServletResponse response) {
        Group group = groupService.findById(id);
        if (group == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!visitor.isLoggedIn() || !group.getCreator().getId().equals(visitor.getUser().getId())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        groupService.deleteGroup(visitor.getUser(), group);
    }

}
