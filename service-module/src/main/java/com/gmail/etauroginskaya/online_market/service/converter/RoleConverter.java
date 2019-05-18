package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;

public interface RoleConverter {

    RoleDTO toDTO(Role role);

    Role fromDTO(RoleDTO roleDTO);
}
