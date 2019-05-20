package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {

    User getUserByEmail(Connection connection, String email);

    User getUserByID(Connection connection, Long id);

    List<User> getUsersBatchByEmailAsc(Connection connection, int page, int quantityUsersInBatch);

    int getQuantityUsersNotDeleted(Connection connection);

    int deleteUsers(Connection connection, List<Long> listID);

    void addUser(Connection connection, User user);

    int updateUserPassword(Connection connection, Long id, String newPassword);

    int updateUserRole(Connection connection, Long userID, Long newRoleID);
}
