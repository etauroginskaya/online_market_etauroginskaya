package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.RoleService;
import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import com.gmail.etauroginskaya.springbootmodule.controller.controller.model.UserUpdateActionEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.USERS_ADD_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.USERS_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.*;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.*;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(USERS_URL)
    public String getViewUsers(Model model,
                               @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                               @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<UserDTO> userPage = userService.getUsersPageByEmailAsc(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("userPage", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        List<RoleDTO> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return USERS_PAGE;
    }

    @PostMapping(USERS_UPDATE_URL)
    public String updateUser(@RequestParam("id") Long id,
                             @RequestParam(value = "roleID", required = false) Long newRoleID,
                             @RequestParam(value = "action") String action) {
        Integer result = null;
        action = action.substring(1);
        if (action.equals(UserUpdateActionEnum.PASSWORD.name())) {
            result = userService.updateUserPassword(id);
        }
        if (action.equals(UserUpdateActionEnum.ROLE.name())) {
            result = userService.updateUserRole(id, newRoleID);
            if (result == null) {
                return "redirect:" + USERS_URL + UPDATE_USER_UNAVAILABLE;
            }
        }
        if (result > 0) {
            return "redirect:" + USERS_URL + UPDATE_USER_SUCCESSFULLY;
        }
        return "redirect:" + USERS_URL + UPDATE_USER_NOT_SUCCESSFULLY;
    }

    @GetMapping(USERS_ADD_URL)
    public String getAddUserView(Model model,
                                 @ModelAttribute(name = "user") UserDTO userDTO) {
        List<RoleDTO> roles = roleService.getRoles();
        model.addAttribute("roles", roles);
        return USERS_ADD_PAGE;
    }

    @PostMapping(USERS_ADD_URL)
    public String addUser(@Valid @ModelAttribute(name = "user") UserDTO userDTO,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            List<RoleDTO> roles = roleService.getRoles();
            model.addAttribute("roles", roles);
            return USERS_ADD_PAGE;
        }
        userService.addUser(userDTO);
        return "redirect:" + USERS_URL + ADD_USERS_SUCCESSFULLY;
    }

    @PostMapping(USERS_DELETE_URL)
    public String deleteUsers(@RequestParam(value = "listID", defaultValue = "") Long[] listID) {
        if (listID.length > 0) {
            int result = userService.deleteUsers(Arrays.asList(listID));
            if (result > 0) {
                return "redirect:" + USERS_URL + DELETE_USERS_SUCCESSFULLY;
            }
        }
        return "redirect:" + USERS_URL + DELETE_USERS_NOT_SUCCESSFULLY;
    }
}
