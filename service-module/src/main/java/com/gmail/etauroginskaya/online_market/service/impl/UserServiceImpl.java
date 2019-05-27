package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.UserRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Profile;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.EmailService;
import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
import com.gmail.etauroginskaya.online_market.service.model.ProfileDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import com.gmail.etauroginskaya.online_market.service.util.CoderUtil;
import com.gmail.etauroginskaya.online_market.service.util.PassGenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Long ID_UNVAILABLE_USER = 1L;
    private static final Integer PASSWORD_LENGTH = 8;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PassGenUtil passGenUtil;
    private final CoderUtil coderUtil;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           PassGenUtil passGenUtil, CoderUtil coderUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passGenUtil = passGenUtil;
        this.coderUtil = coderUtil;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        UserDTO findUser = userConverter.toDTO(user);
        return findUser;
    }

    @Override
    @Transactional
    public Page<UserDTO> getUsersPageByEmailAsc(int pageSize, int currentPage) {
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
        Page<UserDTO> userPage = new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityUsers);
        return userPage;
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
            String password = passGenUtil.getPassword(PASSWORD_LENGTH);
            user.setPassword(coderUtil.encode(password));
            sendUpdatedPasswordToEmail(user, password);
        } else if (changePass) {
            user.setPassword(coderUtil.encode(userDTO.getPassword()));
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
        String password = passGenUtil.getPassword(PASSWORD_LENGTH);
        userDTO.setPassword(coderUtil.encode(password));
        User user = userConverter.fromDTO(userDTO);
        Profile profile = new Profile();
        profile.setUser(user);
        user.setProfile(profile);
        userRepository.persist(user);
        String message = String.format("Hello %s!\n The new registered account is assigned a password: %s",
                userDTO.getName(), password);
        emailService.sendMessage(userDTO.getEmail(), "Market account information", message);
    }

    @Override
    @Transactional
    public int updateUserPassword(Long id) {
        User user = userRepository.getById(id);
        String password = passGenUtil.getPassword(PASSWORD_LENGTH);
        int result = userRepository.updateUserPasswordById(id, coderUtil.encode(password));
        sendUpdatedPasswordToEmail(user, password);
        return result;
    }

    private void sendUpdatedPasswordToEmail(User user, String password) {
        String message = String.format("Hello %s!\n " +
                "Your password has been updated. New password: %s", user.getName(), password);
        emailService.sendMessage(user.getEmail(), "Market account information", message);
    }
}
