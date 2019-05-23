package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.model.AppUserPrincipal;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import com.gmail.etauroginskaya.online_market.service.util.CoderUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.PROFILE_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.UPDATE_USER_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.USER_NEW_PASSWORD_NOT_EQUAL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.USER_PASSWORD_INVALID;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.USER_PASSWORD_NOT_ENTER;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.PROFILE_URL;

@Controller
@RequestMapping
public class ProfileController {

    private final UserService userService;
    private final CoderUtil coderUtil;

    public ProfileController(UserService userService, CoderUtil coderUtil) {
        this.userService = userService;
        this.coderUtil = coderUtil;
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
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return PROFILE_PAGE;
        }
        newPassword = newPassword.substring(1);
        repeatPassword = repeatPassword.substring(1);
        boolean changePass = false;
        if (!newPassword.isEmpty() || !repeatPassword.isEmpty() || random) {
            if (!userDTO.getPassword().isEmpty()) {
                if (newPassword.equals(repeatPassword) || random) {
                    String dbPassword = userService.getPasswordByUserId(userPrincipal.getId());
                    if (coderUtil.checkPassword(userDTO.getPassword(), dbPassword)) {
                        if (!random) {
                            changePass = true;
                            userDTO.setPassword(newPassword);
                        }
                    } else {
                        return "redirect:" + PROFILE_URL + USER_PASSWORD_INVALID;
                    }
                } else {
                    return "redirect:" + PROFILE_URL + USER_NEW_PASSWORD_NOT_EQUAL;
                }
            } else {
                return "redirect:" + PROFILE_URL + USER_PASSWORD_NOT_ENTER;
            }
        }
        userDTO.setId(userPrincipal.getId());
        userService.updateUserWithProfile(userDTO, random, changePass);
        return "redirect:" + PROFILE_URL + UPDATE_USER_SUCCESSFULLY;
    }
}