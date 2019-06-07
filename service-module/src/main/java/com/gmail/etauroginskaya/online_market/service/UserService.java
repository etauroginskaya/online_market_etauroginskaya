package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.data.domain.Page;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;
=======
>>>>>>> develop

import java.util.List;

public interface UserService {

<<<<<<< HEAD
    UserDTO findUserByEmail(String email);

    Page<UserDTO> getUsersPageByEmailAsc(Pageable pageable);

    int deleteUsers(List<Long> listID);
=======
    UserDTO getUserByEmail(String email);

    Page<UserDTO> getUsersPageByEmailAsc(int pageSize, int currentPage);

    int deleteUsersById(List<Long> listID);
>>>>>>> develop

    void addUser(UserDTO userDTO);

    int updateUserPassword(Long id);

<<<<<<< HEAD
    Integer updateUserRole(Long userID, Long newRoleID);
=======
    Integer updateUserRoleById(Long userID, Long newRoleID);

    UserDTO getUserWithProfileById(Long id);

    void updateUserWithProfile(UserDTO userDTO, boolean isRandomPass, boolean changePass);

    String getPasswordByUserId(Long id);
>>>>>>> develop
}
