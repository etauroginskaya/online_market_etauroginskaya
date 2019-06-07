package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_USER_URL;


@RestController
public class UserAPIController {

    private final UserService userService;

    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(API_USER_URL)
    public void saveUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
    }
}
