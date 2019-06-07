package com.gmail.etauroginskaya.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

<<<<<<< HEAD
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_USERS_NOT_SUCCESSFULLY;
=======
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_NOT_SUCCESSFULLY;
>>>>>>> develop
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.UPDATE_USER_UNAVAILABLE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.ADMIN_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerSecureIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldSucceedForUsersPage() throws Exception {
        mvc.perform(get(USERS_URL))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldSucceedForUsersAddPage() throws Exception {
        mvc.perform(get(USERS_ADD_URL))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldSucceedForUserAdd() throws Exception {
        mvc.perform(post(USERS_ADD_URL))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldSucceedWith302ForUserRoleUpdate() throws Exception {
        mvc.perform(post(USERS_UPDATE_URL)
                .param("id", "1")
                .param("action", " ROLE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(USERS_URL + UPDATE_USER_UNAVAILABLE));
    }

    @WithMockUser(authorities = {ADMIN_ROLE_NAME})
    @Test
    public void shouldSucceedWith302ForUserDelete() throws Exception {
        mvc.perform(post(USERS_DELETE_URL)
                .param("listID", "1"))
                .andExpect(status().is3xxRedirection())
<<<<<<< HEAD
                .andExpect(redirectedUrl(USERS_URL + DELETE_USERS_NOT_SUCCESSFULLY));
=======
                .andExpect(redirectedUrl(USERS_URL + DELETE_NOT_SUCCESSFULLY));
>>>>>>> develop
    }
}
