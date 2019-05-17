package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.RoleRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.service.RoleService;
import com.gmail.etauroginskaya.online_market.service.converter.RoleConverter;
import com.gmail.etauroginskaya.online_market.service.exception.ServiceException;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private static final String CONNECTION_ERROR_MESSAGE = "Connection Failed! Check output console.";
    private static final String TRANSACTION_ERROR_MESSAGE = "Coming transaction Failed! Check output console.";
    private final RoleConverter roleConverter;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleConverter roleConverter, RoleRepository roleRepository) {
        this.roleConverter = roleConverter;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> getRoles() {
        try (Connection connection = roleRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Role> roles = roleRepository.getRoles(connection);
                List<RoleDTO> dtos = roles.stream()
                        .map(roleConverter::toDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return dtos;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(TRANSACTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(CONNECTION_ERROR_MESSAGE, e);
        }
    }
}
