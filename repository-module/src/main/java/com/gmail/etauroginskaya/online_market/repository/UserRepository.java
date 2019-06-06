package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.User;

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
}
