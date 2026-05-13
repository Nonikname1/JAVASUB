package repository.impl;

import model.Group;
import repository.GroupRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryGroupRepository implements GroupRepository {
    private final Map<Integer, Group> store = new HashMap<>();

    @Override
    public void save(Group group) {
        store.put(group.getId(), group);
    }

    @Override
    public Optional<Group> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Group> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(int id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(int id) {
        return store.containsKey(id);
    }
}
