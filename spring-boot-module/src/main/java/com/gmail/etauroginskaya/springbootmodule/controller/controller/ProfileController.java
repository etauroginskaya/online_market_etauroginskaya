package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.model.AppUserPrincipal;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.PROFILE_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.PROFILE_URL;

@Controller
@RequestMapping
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(PROFILE_URL)
    public String getProfile(Model model,
                             @AuthenticationPrincipal AppUserPrincipal userPrincipal) {
        UserDTO userDTO = userService.getUserWithProfileById(userPrincipal.getId());
        model.addAttribute("user", userDTO);
        return PROFILE_PAGE;
    }

    @PostMapping(PROFILE_URL)
    public String updateProfile(@AuthenticationPrincipal AppUserPrincipal userPrincipal,
                                @Valid @ModelAttribute(name = "user") UserDTO userDTO,
                                @RequestParam(value = "newPassword", defaultValue = "") String newPassword,
                                @RequestParam(value = "repeatPassword", defaultValue = "") String repeatPassword,
                                @RequestParam(value = "random", defaultValue = "false") Boolean random,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return PROFILE_PAGE;
        }
        Long id = userPrincipal.getId();
        userDTO.setId(id);
        if (userPrincipal.getPassword().equals(userDTO.getPassword())) {
            if (random) {
                userService.updateUserPassword(id);
            } else {
                if (newPassword == repeatPassword) {
                    userDTO.setPassword(newPassword.substring(1));
                }
            }
        }
        UserDTO oldUser = userService.getUserWithProfileById(id);
        userDTO.setRole(oldUser.getRole());
        userDTO.getProfileDTO().setUserId(id);
        userService.updateUserWithProfile(userDTO);
        return PROFILE_PAGE;
    }
}
