package service;

import model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student createStudent(String name, int groupId);
    void deleteStudent(int id);
    List<Student> getStudentsByGroup(int groupId);
    void updateTaskStatus(int studentId, int taskIndex, boolean status);
    Optional<Student> getStudentById(int id);
}
