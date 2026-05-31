package repository.impl;

import db.ConnectionFactory;
import model.Student;
import repository.StudentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcStudentRepository implements StudentRepository {

    @Override
    public void save(Student student) {
        String sql = "INSERT INTO students(id, name, group_id, task1, task2, task3) VALUES(?, ?, ?, ?, ?, ?) " +
                     "ON CONFLICT(id) DO UPDATE SET " +
                     "name = EXCLUDED.name, group_id = EXCLUDED.group_id, " +
                     "task1 = EXCLUDED.task1, task2 = EXCLUDED.task2, task3 = EXCLUDED.task3";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, student.getId());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getGroupId());
            ps.setBoolean(4, student.getTaskStatus(0));
            ps.setBoolean(5, student.getTaskStatus(1));
            ps.setBoolean(6, student.getTaskStatus(2));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении студента", e);
        }
    }

    @Override
    public Optional<Student> findById(int id) {
        String sql = "SELECT id, name, group_id, task1, task2, task3 FROM students WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске студента", e);
        }
    }

    @Override
    public List<Student> findByGroupId(int groupId) {
        String sql = "SELECT id, name, group_id, task1, task2, task3 FROM students WHERE group_id = ? ORDER BY id";
        List<Student> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске студентов группы", e);
        }
        return result;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении студента", e);
        }
    }

    @Override
    public void deleteByGroupId(int groupId) {
        String sql = "DELETE FROM students WHERE group_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении студентов группы", e);
        }
    }

    @Override
    public int findMaxId() {
        String sql = "SELECT COALESCE(MAX(id), 0) FROM students";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении max id студентов", e);
        }
    }

    private Student map(ResultSet rs) throws SQLException {
        Student s = new Student(rs.getInt("id"), rs.getString("name"), rs.getInt("group_id"));
        s.setTaskStatus(0, rs.getBoolean("task1"));
        s.setTaskStatus(1, rs.getBoolean("task2"));
        s.setTaskStatus(2, rs.getBoolean("task3"));
        return s;
    }
}
