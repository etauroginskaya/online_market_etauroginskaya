package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDTO findUserByEmail(String email);

    Page<UserDTO> getUsersInPage(Pageable pageable);

    Integer deleteListUsers(List<Long> listID);

    void addUser(UserDTO userDTO);

    List<RoleDTO> getListRoles();

    Integer updateUserPassword(Long id);

    Integer updateUserRole(Long userID, Long newRoleID);
}
