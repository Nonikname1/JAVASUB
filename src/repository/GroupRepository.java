package repository;

import model.Group;
import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    void save(Group group);
    Optional<Group> findById(int id);
    List<Group> findAll();
    void deleteById(int id);
    boolean existsById(int id);
}
