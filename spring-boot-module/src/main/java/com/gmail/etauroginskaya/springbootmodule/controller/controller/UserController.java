package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.USERS_ADD_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.USERS_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.*;

@Controller
@RequestMapping
public class UserController {

    private static final Integer DEFAULT_NUMBER_START_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(USERS_URL)
    public String getListUsers(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_NUMBER_START_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        Page<UserDTO> userPage = userService.getUsersInPage(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("userPage", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        List<RoleDTO> roles = userService.getListRoles();
        model.addAttribute("roles", roles);
        return USERS_PAGE;
    }

    @PostMapping(USERS_UPDATE_URL)
    public String update(@RequestParam("id") Long id,
                         @RequestParam(value = "roleID", required = false) Long newRoleID,
                         @RequestParam(value = "action") String action) {
        Integer result = null;
        switch (action) {
            case ",password":
                result = userService.updateUserPassword(id);
                break;
            case ",role":
                if (newRoleID != null) {
                    result = userService.updateUserRole(id, newRoleID);
                }
                break;
        }
        if (result == 1) {
            return "redirect:" + USERS_URL + "?updated";
        }
        return "redirect:" + USERS_URL + "?not_updated";
    }

    @GetMapping(USERS_ADD_URL)
    public String getAddUserForm(Model model,
                                 @ModelAttribute(name = "user") UserDTO userDTO) {
        List<RoleDTO> roles = userService.getListRoles();
        model.addAttribute("roles", roles);
        return USERS_ADD_PAGE;
    }

    @PostMapping(USERS_ADD_URL)
    public String addUser(@Valid @ModelAttribute(name = "user") UserDTO userDTO,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            List<RoleDTO> roles = userService.getListRoles();
            model.addAttribute("roles", roles);
            return USERS_ADD_PAGE;
        }
        userService.addUser(userDTO);
        return "redirect:" + USERS_URL;
    }

    @PostMapping(USERS_DELETE_URL)
    public String deleteUsers(@RequestParam("listID") Long[] listID) {
        Integer result = userService.deleteListUsers(Arrays.asList(listID));
        if (result == 0) {
            return "redirect:" + USERS_URL + "?not_deleted";
        }
        return "redirect:" + USERS_URL + "?deleted";
    }
}
