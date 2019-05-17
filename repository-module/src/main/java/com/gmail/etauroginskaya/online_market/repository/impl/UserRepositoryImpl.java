package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.repository.UserRepository;
import com.gmail.etauroginskaya.online_market.repository.exception.DatabaseQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl extends ConnectionRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String QUERY_ERROR_MESSAGE = "SQL query Failed! Check output console.";

    @Override
    public User getUserByEmail(Connection connection, String email) {
        String sql = "SELECT u.*, r.name as name_role  FROM user u JOIN role r ON u.role_id = r.id " +
                "WHERE (email = ? AND deleted = 'false')";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public User getUserByID(Connection connection, Long id) {
        String sql = "SELECT u.*, r.name as name_role  FROM user u JOIN role r ON u.role_id = r.id " +
                "WHERE (u.id = ? AND deleted = 'false')";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public List<User> getUsersBatchByEmailAsc(Connection connection, int page, int quantityUsersInBatch) {
        String sql = "SELECT u.*, r.name as name_role  FROM user u JOIN role r ON u.role_id = r.id " +
                "WHERE deleted = 'false' ORDER BY u.email LIMIT ?, ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, page * quantityUsersInBatch);
            statement.setInt(2, quantityUsersInBatch);
            try (ResultSet rs = statement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(getUser(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public int getQuantityUsersNotDeleted(Connection connection) {
        String sql = "SELECT COUNT(*) AS total FROM user WHERE deleted = 'false'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("total");
            }
            return count;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public int deleteUsers(Connection connection, List<Long> listID) {
        String sql = "UPDATE user SET deleted = true WHERE id IN (?)";
        sql = anyParams(sql, listID.size());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Long num : listID) {
                statement.setLong(i++, num);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Deleting user failed, no row affected.");
                throw new DatabaseQueryException("Deleting user failed, no row affected.");
            }
            return affectedRows;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    private String anyParams(String sql, final int params) {
        final StringBuilder sb = new StringBuilder(new String(new char[params]).replace("\0", "?,"));
        sb.setLength(Math.max(sb.length() - 1, 0));
        if (sb.length() > 1) {
            sql = sql.replace("(?)", "(" + sb + ")");
        }
        return sql;
    }

    @Override
    public int updateUserRole(Connection connection, Long userID, Long newRoleID) {
        String sql = "UPDATE user SET role_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, newRoleID);
            statement.setLong(2, userID);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Updating user role failed, no row affected.");
                throw new DatabaseQueryException("Updating user role failed, no row affected.");
            }
            return affectedRows;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public void addUser(Connection connection, User user) {
        String sql = "INSERT INTO user(surname, name, patronymic, email, password, role_id, deleted) VALUES(?, ?, ?, ?, ?, ?, false)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getSurname());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPatronymic());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setLong(6, user.getRole().getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Creating user failed, no row affected.");
                throw new DatabaseQueryException("Creating user failed, no row affected.");
            }
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (!rs.next()) {
                    logger.error("Creating user failed, no ID obtained.");
                    throw new DatabaseQueryException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public int updateUserPassword(Connection connection, Long id, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Updating user password failed, no row affected.");
                throw new DatabaseQueryException("Updating user password failed, no row affected.");
            }
            return affectedRows;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setSurname(resultSet.getString("surname"));
        user.setName(resultSet.getString("name"));
        user.setPatronymic(resultSet.getString("patronymic"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        Role role = new Role();
        role.setId(resultSet.getLong("role_id"));
        role.setName(resultSet.getString("name_role"));
        user.setRole(role);
        return user;
    }
}
