package repository.impl;

import db.ConnectionFactory;
import model.Group;
import repository.GroupRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGroupRepository implements GroupRepository {

    @Override
    public void save(Group group) {
        String sql = "INSERT INTO groups(id, name) VALUES(?, ?) " +
                     "ON CONFLICT(id) DO UPDATE SET name = EXCLUDED.name";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, group.getId());
            ps.setString(2, group.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении группы", e);
        }
    }

    @Override
    public Optional<Group> findById(int id) {
        String sql = "SELECT id, name FROM groups WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Group(rs.getInt("id"), rs.getString("name")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске группы", e);
        }
    }

    @Override
    public List<Group> findAll() {
        String sql = "SELECT id, name FROM groups ORDER BY id";
        List<Group> result = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new Group(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка групп", e);
        }
        return result;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM groups WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении группы", e);
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM groups WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке группы", e);
        }
    }

    @Override
    public int findMaxId() {
        String sql = "SELECT COALESCE(MAX(id), 0) FROM groups";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении max id групп", e);
        }
    }
}
