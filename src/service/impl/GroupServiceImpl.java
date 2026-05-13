package service.impl;

import model.Group;
import repository.GroupRepository;
import repository.StudentRepository;
import service.GroupService;

import java.util.List;
import java.util.Optional;

public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private int nextId = 1;

    public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Group createGroup(String name) {
        Group group = new Group(nextId++, name);
        groupRepository.save(group);
        return group;
    }

    @Override
    public void deleteGroup(int id) {
        groupRepository.deleteById(id);
        studentRepository.deleteByGroupId(id);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> getGroupById(int id) {
        return groupRepository.findById(id);
    }
}
