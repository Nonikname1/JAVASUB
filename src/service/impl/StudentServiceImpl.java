package service.impl;

import model.Student;
import repository.GroupRepository;
import repository.StudentRepository;
import service.StudentService;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private int nextId = 1;

    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Student createStudent(String name, int groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Группа не найдена: " + groupId);
        }
        Student student = new Student(nextId++, name, groupId);
        studentRepository.save(student);
        return student;
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByGroup(int groupId) {
        return studentRepository.findByGroupId(groupId);
    }

    @Override
    public void updateTaskStatus(int studentId, int taskIndex, boolean status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Студент не найден: " + studentId));
        student.setTaskStatus(taskIndex, status);
    }

    @Override
    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }
}
