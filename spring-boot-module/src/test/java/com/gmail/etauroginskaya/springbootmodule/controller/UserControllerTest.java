package com.gmail.etauroginskaya.springbootmodule.controller;

import com.gmail.etauroginskaya.online_market.service.RoleService;
import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.model.RoleDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import com.gmail.etauroginskaya.springbootmodule.controller.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.USERS_ADD_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.*;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.USERS_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.USERS_URL;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
<<<<<<< HEAD
=======
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
>>>>>>> develop
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private BindingResult bindingResult;

    private UserController userController;
    private MockMvc mockMvc;

    private List<RoleDTO> roleDTOS = asList(new RoleDTO(), new RoleDTO());

    @Before
    public void init() {
        userController = new UserController(userService, roleService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(roleService.getRoles()).thenReturn(roleDTOS);
    }

    @Test
    public void shouldGetAddUserViewWithSomeRoles() throws Exception {
        this.mockMvc.perform(get(USERS_ADD_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", equalTo(roleDTOS)));
    }

    @Test
<<<<<<< HEAD
    public void shouldGetAddUserPageWithSomeRoles() throws Exception {
        this.mockMvc.perform(post(USERS_ADD_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute("roles", equalTo(roleDTOS)));
    }

    @Test
    public void shouldRedirectToFormAddUserWithSomeRolesAfterInvalidAdded() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        this.mockMvc.perform(post(USERS_ADD_URL))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(USERS_ADD_PAGE))
                .andExpect(model().attribute("roles", equalTo(roleDTOS)));
    }

    @Test
=======
>>>>>>> develop
    public void shouldRedirectToFormUsersAfterSuccessfullyAdded() {
        when(bindingResult.hasErrors()).thenReturn(false);
        Model model = new ExtendedModelMap();
        String pageAfterAdd = userController.addUser(new UserDTO(), bindingResult, model);
<<<<<<< HEAD
        assertThat(pageAfterAdd, equalTo("redirect:".concat(USERS_URL).concat(ADD_USERS_SUCCESSFULLY)));
=======
        assertThat(pageAfterAdd, equalTo("redirect:".concat(USERS_URL).concat(ADD_SUCCESSFULLY)));
>>>>>>> develop
    }

    @Test
    public void shouldRedirectToUsersPageWithSuccessfullyParameterAfterSuccessfullyDeletedUser(){
        Long[] longArray = {1L, 2L};
<<<<<<< HEAD
        when(userService.deleteUsers(Arrays.asList(longArray))).thenReturn(1);
        String resultUrl = userController.deleteUsers(longArray);
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(DELETE_USERS_SUCCESSFULLY)));
=======
        when(userService.deleteUsersById(Arrays.asList(longArray))).thenReturn(1);
        String resultUrl = userController.deleteUsers(longArray);
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(DELETE_SUCCESSFULLY)));
>>>>>>> develop
    }

    @Test
    public void shouldRedirectToUsersPageWithNotSuccessfullyParameterAfterInvalidDeletedUser(){
        Long[] longArray = {1L, 2L};
<<<<<<< HEAD
        when(userService.deleteUsers(Arrays.asList(longArray))).thenReturn(0);
        String resultUrl = userController.deleteUsers(longArray);
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(DELETE_USERS_NOT_SUCCESSFULLY)));
=======
        when(userService.deleteUsersById(Arrays.asList(longArray))).thenReturn(0);
        String resultUrl = userController.deleteUsers(longArray);
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(DELETE_NOT_SUCCESSFULLY)));
>>>>>>> develop
    }

    @Test
    public void shouldRedirectToUsersPageWithSuccessfullyParameterAfterSuccessfullyUpdatedUserPassword(){
        when(userService.updateUserPassword(1L)).thenReturn(1);
        String resultUrl = userController.updateUser(1L,1L," PASSWORD");
<<<<<<< HEAD
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_USER_SUCCESSFULLY)));
=======
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_SUCCESSFULLY)));
>>>>>>> develop
    }

    @Test
    public void shouldRedirectToUsersPageWithNotSuccessfullyParameterAfterInvalidUpdatedUserPassword(){
        when(userService.updateUserPassword(1L)).thenReturn(0);
        String resultUrl = userController.updateUser(1L,1L," PASSWORD");
<<<<<<< HEAD
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_USER_NOT_SUCCESSFULLY)));
=======
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_NOT_SUCCESSFULLY)));
>>>>>>> develop
    }

    @Test
    public void shouldRedirectToUsersPageWithSuccessfullyParameterAfterSuccessfullyUpdatedUserRole(){
<<<<<<< HEAD
        when(userService.updateUserRole(1L, 1L)).thenReturn(1);
        String resultUrl = userController.updateUser(1L,1L," ROLE");
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_USER_SUCCESSFULLY)));
=======
        when(userService.updateUserRoleById(1L, 1L)).thenReturn(1);
        String resultUrl = userController.updateUser(1L,1L," ROLE");
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_SUCCESSFULLY)));
>>>>>>> develop
    }

    @Test
    public void shouldRedirectToUsersPageWithNotSuccessfullyParameterAfterInvalidUpdatedUserRole(){
<<<<<<< HEAD
        when(userService.updateUserRole(1L,1L)).thenReturn(0);
        String resultUrl = userController.updateUser(1L,1L," ROLE");
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_USER_NOT_SUCCESSFULLY)));
=======
        when(userService.updateUserRoleById(1L,1L)).thenReturn(0);
        String resultUrl = userController.updateUser(1L,1L," ROLE");
        assertThat(resultUrl, equalTo("redirect:".concat(USERS_URL).concat(UPDATE_NOT_SUCCESSFULLY)));
>>>>>>> develop
    }
}