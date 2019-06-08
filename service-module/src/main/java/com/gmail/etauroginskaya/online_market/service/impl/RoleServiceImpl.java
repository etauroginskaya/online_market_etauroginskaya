package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.RoleRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.service.RoleService;
import com.gmail.etauroginskaya.online_market.service.converter.RoleConverter;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleConverter roleConverter;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleConverter roleConverter, RoleRepository roleRepository) {
        this.roleConverter = roleConverter;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public List<RoleDTO> getRoles() {
        List<Role> roles = roleRepository.getAll();
        return roles.stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList());
    }
}