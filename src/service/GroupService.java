package service;

import model.Group;
import java.util.List;
import java.util.Optional;

public interface GroupService {
    Group createGroup(String name);
    void deleteGroup(int id);
    List<Group> getAllGroups();
    Optional<Group> getGroupById(int id);
}
