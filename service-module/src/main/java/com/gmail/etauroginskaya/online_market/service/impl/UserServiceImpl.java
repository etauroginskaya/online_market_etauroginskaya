package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.UserRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Role;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.converter.RoleConverter;
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
import com.gmail.etauroginskaya.online_market.service.exception.ServiceException;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import com.gmail.etauroginskaya.online_market.service.util.PassGenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String CONNECTION_ERROR_MESSAGE = "Connection Failed! Check output console.";
    private static final String TRANSACTION_ERROR_MESSAGE = "Coming transaction Failed! Check output console.";
    private static final Long ID_UNVAILABLE_USER = 1L;
    private static final Integer PASSWORD_LENGTH = 8;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleConverter roleConverter;
    private final PassGenUtil passGenUtil;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           RoleConverter roleConverter, PassGenUtil passGenUtil) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.roleConverter = roleConverter;
        this.passGenUtil = passGenUtil;
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByEmail(connection, email);
                UserDTO findUser = userConverter.toDTO(user);
                connection.commit();
                return findUser;
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

    @Override
    public Page<UserDTO> getUsersInPage(Pageable pageable) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int pageSize = pageable.getPageSize();
                int currentPage = pageable.getPageNumber();
                int startItem = currentPage * pageSize;
                int quantityUsers = userRepository.getQuantityUsers(connection);
                List<User> users;
                List<UserDTO> dtos;
                if (quantityUsers < startItem) {
                    dtos = Collections.emptyList();
                } else {
                    users = userRepository.getUsersBatchByEmailAsc(connection, currentPage, pageSize);
                    dtos = users.stream().map(userConverter::toDTO).collect(Collectors.toList());
                    connection.commit();
                }
                Page<UserDTO> userPage = new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityUsers);
                connection.commit();
                return userPage;
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

    @Override
    public Integer deleteListUsers(List<Long> listID) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Long> listIDForDelete = listID.stream()
                        .filter(id -> !id.equals(ID_UNVAILABLE_USER))
                        .collect(Collectors.toList());
                Integer result = userRepository.deleteListUsers(connection, listIDForDelete);
                connection.commit();
                return result;
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

    @Override
    public Integer updateUserRole(Long userID, Long newRoleID) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                if (!userID.equals(ID_UNVAILABLE_USER)) {
                    Integer result = userRepository.updateUserRole(connection, userID, newRoleID);
                    connection.commit();
                    return result;
                }
                return 0;
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

    @Override
    public void addUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userDTO.setPassword(passGenUtil.getPassword(PASSWORD_LENGTH, userDTO.getEmail()));
                User userForSave = userConverter.fromDTO(userDTO);
                userRepository.addUser(connection, userForSave);
                connection.commit();
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

    @Override
    public List<RoleDTO> getListRoles() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Role> roles = userRepository.getListRoles(connection);
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

    @Override
    public Integer updateUserPassword(Long id) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                String userEmail = userRepository.getUserByID(connection, id).getEmail();
                String genPassword = passGenUtil.getPassword(PASSWORD_LENGTH, userEmail);
                Integer result = userRepository.updateUserPassword(connection, id, genPassword);
                connection.commit();
                return result;
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
