package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.User;

<<<<<<< HEAD
import java.sql.Connection;
import java.util.List;

public interface UserRepository extends ConnectionRepository {

    User getUserByEmail(Connection connection, String email);

    User getUserByID(Connection connection, Long id);

    List<User> getUsersBatchByEmailAsc(Connection connection, int page, int quantityUsersInBatch);

    int getQuantityUsersNotDeleted(Connection connection);

    int deleteUsers(Connection connection, List<Long> listID);

    void addUser(Connection connection, User user);

    int updateUserPassword(Connection connection, Long id, String newPassword);

    int updateUserRole(Connection connection, Long userID, Long newRoleID);
=======
import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {

    User getUserByEmail(String email);

    List<User> getUsersByEmailAsc(int page, int maxResult);

    int deleteUsersById(List<Long> listID);

    void deleteProfilesById(List<Long> listID);

    int updateUserPasswordById(Long id, String newPassword);

    int updateUserRoleById(Long userID, Long newRoleID);

    String getUserPasswordById(Long id);

    boolean existsByEmail(String email);
>>>>>>> develop
}
