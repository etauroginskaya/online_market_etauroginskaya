package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.UserRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Long ID_UNVAILABLE_USER = 1L;
    private static final Integer PASSWORD_LENGTH = 8;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final GeneratorService generatorService;
    private final CoderService coderService;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           GeneratorService generatorService, CoderService coderService, EmailService emailService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.generatorService = generatorService;
        this.coderService = coderService;
        this.emailService = emailService;
    }

    @Override
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
    }
}