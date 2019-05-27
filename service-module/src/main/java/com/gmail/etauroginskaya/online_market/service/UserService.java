package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserDTO getUserByEmail(String email);

    Page<UserDTO> getUsersPageByEmailAsc(int pageSize, int currentPage);

    int deleteUsersById(List<Long> listID);

    void addUser(UserDTO userDTO);

    int updateUserPassword(Long id);

    Integer updateUserRoleById(Long userID, Long newRoleID);

    UserDTO getUserWithProfileById(Long id);

    void updateUserWithProfile(UserDTO userDTO, boolean isRandomPass, boolean changePass);

    String getPasswordByUserId(Long id);
}
