package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends ConnectionRepository {

    User getUserByEmail(Connection connection, String email);

    User getUserByID(Connection connection, Long id);

    List<User> getUsersBatchByEmailAsc(Connection connection, int page, int quantityUsersInBatch);

    Integer getQuantityUsers(Connection connection);

    Integer deleteListUsers(Connection connection, List<Long> listID);

    void addUser(Connection connection, User user);

    List<Role> getListRoles(Connection connection);

    Integer updateUserPassword(Connection connection, Long id, String newPassword);

    Integer updateUserRole(Connection connection, Long userID, Long newRoleID);
}
