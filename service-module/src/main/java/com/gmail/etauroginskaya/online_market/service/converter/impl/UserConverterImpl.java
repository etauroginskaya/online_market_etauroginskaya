package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.converter.RoleConverter;
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    private final RoleConverter roleConverter;

    public UserConverterImpl(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setSurname(user.getSurname());
        userDTO.setName(user.getName());
<<<<<<< HEAD
        userDTO.setPatronymic(user.getPatronymic());
=======
>>>>>>> develop
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        RoleDTO roleDTO = roleConverter.toDTO(user.getRole());
        userDTO.setRole(roleDTO);
        return userDTO;
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
<<<<<<< HEAD
        user.setPatronymic(userDTO.getPatronymic());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        Role role = roleConverter.fromDTO(userDTO.getRole());
        user.setRole(role);
=======
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getRole() != null) {
            Role role = roleConverter.fromDTO(userDTO.getRole());
            user.setRole(role);
        }
>>>>>>> develop
        return user;
    }
}
