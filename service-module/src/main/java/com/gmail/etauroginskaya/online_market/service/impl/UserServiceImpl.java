package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.UserRepository;
<<<<<<< HEAD
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.EmailService;
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
import com.gmail.etauroginskaya.online_market.service.exception.ServiceException;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import com.gmail.etauroginskaya.online_market.service.util.CoderUtil;
import com.gmail.etauroginskaya.online_market.service.util.PassGenUtil;
import com.gmail.etauroginskaya.online_market.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
=======
import com.gmail.etauroginskaya.online_market.repository.model.Profile;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.EmailService;
import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
import com.gmail.etauroginskaya.online_market.service.model.ProfileDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import com.gmail.etauroginskaya.online_market.service.CoderService;
import com.gmail.etauroginskaya.online_market.service.GeneratorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

>>>>>>> develop
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

<<<<<<< HEAD
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String CONNECTION_ERROR_MESSAGE = "Connection Failed! Check output console.";
    private static final String TRANSACTION_ERROR_MESSAGE = "Coming transaction Failed! Check output console.";
=======
>>>>>>> develop
    private static final Long ID_UNVAILABLE_USER = 1L;
    private static final Integer PASSWORD_LENGTH = 8;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
<<<<<<< HEAD
    private final PassGenUtil passGenUtil;
    private final CoderUtil coderUtil;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           PassGenUtil passGenUtil, CoderUtil coderUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passGenUtil = passGenUtil;
        this.coderUtil = coderUtil;
=======
    private final GeneratorService generatorService;
    private final CoderService coderService;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           GeneratorService generatorService, CoderService coderService, EmailService emailService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.generatorService = generatorService;
        this.coderService = coderService;
>>>>>>> develop
        this.emailService = emailService;
    }

    @Override
<<<<<<< HEAD
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
    public Page<UserDTO> getUsersPageByEmailAsc(Pageable pageable) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int pageSize = pageable.getPageSize();
                int currentPage = pageable.getPageNumber();
                int startItem = currentPage * pageSize;
                int quantityUsers = userRepository.getQuantityUsersNotDeleted(connection);
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
    public int deleteUsers(List<Long> listID) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Long> listIDForDelete = listID.stream()
                        .filter(id -> !id.equals(ID_UNVAILABLE_USER))
                        .collect(Collectors.toList());
                int result = 0;
                if (!listIDForDelete.isEmpty()) {
                    result = userRepository.deleteUsers(connection, listIDForDelete);
                }
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
        if (userID.equals(ID_UNVAILABLE_USER)) {
            return null;
        }
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int result = userRepository.updateUserRole(connection, userID, newRoleID);
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
    public void addUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                String password = passGenUtil.getPassword(PASSWORD_LENGTH);
                userDTO.setPassword(coderUtil.encode(password));
                User userForSave = userConverter.fromDTO(userDTO);
                userRepository.addUser(connection, userForSave);
                String message = String.format("Hello %s!\n The new registered account is assigned a password: %s",
                        userDTO.getName(), password);
                emailService.sendMessage(userDTO.getEmail(), "Market account information", message);
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
    public int updateUserPassword(Long id) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getUserByID(connection, id);
                String password = passGenUtil.getPassword(PASSWORD_LENGTH);
                int result = userRepository.updateUserPassword(connection, id, password);
                String message = String.format("Hello %s!\n " +
                        "Your password has been updated. New password: %s", user.getName(), password);
                emailService.sendMessage(user.getEmail(), "Market account information", message);
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
=======
    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        return userConverter.toDTO(user);
    }

    @Override
    @Transactional
    public Page<UserDTO> getUsersPageByEmailAsc(int pageSize, int currentPage) {
        currentPage--;
        int startItem = currentPage * pageSize;
        int quantityUsers = userRepository.getCountOfEntities();
        List<UserDTO> dtos;
        if (quantityUsers < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<User> users = userRepository.getUsersByEmailAsc(currentPage, pageSize);
            dtos = users.stream()
                    .map(userConverter::toDTO)
                    .collect(Collectors.toList());
        }
        return new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityUsers);
    }

    @Override
    @Transactional
    public int deleteUsersById(List<Long> listID) {
        List<Long> listIDForDelete = listID.stream()
                .filter(id -> !id.equals(ID_UNVAILABLE_USER))
                .collect(Collectors.toList());
        int result = 0;
        if (!listIDForDelete.isEmpty()) {
            result = userRepository.deleteUsersById(listIDForDelete);
            userRepository.deleteProfilesById(listIDForDelete);
        }
        return result;
    }

    @Override
    @Transactional
    public Integer updateUserRoleById(Long userID, Long newRoleID) {
        if (userID.equals(ID_UNVAILABLE_USER)) {
            return null;
        }
        return userRepository.updateUserRoleById(userID, newRoleID);
    }

    @Override
    @Transactional
    public UserDTO getUserWithProfileById(Long id) {
        User user = userRepository.getById(id);
        UserDTO userDTO = userConverter.toDTO(user);
        ProfileDTO profileDTO = new ProfileDTO();
        if (user.getProfile() != null) {
            profileDTO.setAddress(user.getProfile().getAddress());
            profileDTO.setTelephone(user.getProfile().getTelephone());
        }
        userDTO.setProfileDTO(profileDTO);
        return userDTO;
    }

    @Override
    @Transactional
    public void updateUserWithProfile(UserDTO userDTO, boolean isRandomPass, boolean changePass) {
        User user = userRepository.getById(userDTO.getId());
        if (isRandomPass) {
            String password = generatorService.getPassword(PASSWORD_LENGTH);
            user.setPassword(coderService.encode(password));
            emailService.sendUpdatedPassword(user, password);
        } else if (changePass) {
            user.setPassword(coderService.encode(userDTO.getPassword()));
        }
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.getProfile().setAddress(userDTO.getProfileDTO().getAddress());
        user.getProfile().setTelephone(userDTO.getProfileDTO().getTelephone());
        userRepository.merge(user);
    }

    @Override
    @Transactional
    public String getPasswordByUserId(Long id) {
        return userRepository.getUserPasswordById(id);
    }

    @Override
    @Transactional
    public void addUser(UserDTO userDTO) {
        String password = generatorService.getPassword(PASSWORD_LENGTH);
        userDTO.setPassword(coderService.encode(password));
        User user = userConverter.fromDTO(userDTO);
        Profile profile = new Profile();
        profile.setUser(user);
        user.setProfile(profile);
        userRepository.persist(user);
        emailService.sendNewUserPassword(userDTO.getEmail(), password);
    }

    @Override
    @Transactional
    public int updateUserPassword(Long id) {
        User user = userRepository.getById(id);
        String password = generatorService.getPassword(PASSWORD_LENGTH);
        int result = userRepository.updateUserPasswordById(id, coderService.encode(password));
        emailService.sendUpdatedPassword(user, password);
        return result;
>>>>>>> develop
    }
}
