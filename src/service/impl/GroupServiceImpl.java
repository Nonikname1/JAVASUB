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
        this.nextId = groupRepository.findMaxId() + 1;
    }

    @Override
    public Group createGroup(String name) {
        Group group = new Group(nextId++, name);
        groupRepository.save(group);
        return group;
    }

    @Override
    public void deleteGroup(int id) {
        studentRepository.deleteByGroupId(id);
        groupRepository.deleteById(id);
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
