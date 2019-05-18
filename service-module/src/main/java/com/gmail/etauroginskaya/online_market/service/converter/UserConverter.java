package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;

public interface UserConverter {

    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}
