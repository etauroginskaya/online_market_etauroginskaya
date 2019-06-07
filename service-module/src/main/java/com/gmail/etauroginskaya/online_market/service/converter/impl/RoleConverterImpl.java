package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.service.converter.RoleConverter;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {
<<<<<<< HEAD
=======

>>>>>>> develop
    @Override
    public RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    @Override
    public Role fromDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
<<<<<<< HEAD
        if (!(roleDTO.getName() == null)) {
=======
        if (roleDTO.getName() != null) {
>>>>>>> develop
            role.setName(roleDTO.getName());
        }
        return role;
    }
}
