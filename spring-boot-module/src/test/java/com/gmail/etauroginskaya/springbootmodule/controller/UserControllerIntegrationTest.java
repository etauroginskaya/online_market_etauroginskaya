package com.gmail.etauroginskaya.springbootmodule.controller;

import com.gmail.etauroginskaya.online_market.service.UserService;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.ADMIN_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.USERS_URL;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private List<UserDTO> dtos = asList(new UserDTO(), new UserDTO());

    @Test
    @WithMockUser(roles = {ADMIN_ROLE_NAME})
    public void shouldGetUsersPage() throws Exception {
        this.mockMvc.perform(get(USERS_URL)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {ADMIN_ROLE_NAME})
    public void shouldGetUsersPageWithSomeUsers() throws Exception {
        this.mockMvc.perform(get(USERS_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", equalTo(dtos)));
    }
}
