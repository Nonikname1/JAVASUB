package repository.impl;

import model.Student;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<Integer, Student> store = new HashMap<>();

    @Override
    public void save(Student student) {
        store.put(student.getId(), student);
    }

    @Override
    public Optional<Student> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Student> findByGroupId(int groupId) {
        List<Student> result = new ArrayList<>();
        for (Student s : store.values()) {
            if (s.getGroupId() == groupId) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public void deleteById(int id) {
        store.remove(id);
    }

    @Override
    public void deleteByGroupId(int groupId) {
        Iterator<Map.Entry<Integer, Student>> it = store.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue().getGroupId() == groupId) {
                it.remove();
            }
        }
    }
}
