package com.gmail.etauroginskaya.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.CUSTOMER_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.SALE_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDERS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDER_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDER_UPDATE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDER_URL;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootModuleApplication.class, DbConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerSecureIntegrationTest {

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

    @WithMockUser(authorities = {SALE_ROLE_NAME, CUSTOMER_ROLE_NAME})
    @Test
    public void shouldSucceedForOrdersPage() throws Exception {
        mvc.perform(get(ORDERS_URL))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForOrder() throws Exception {
        mvc.perform(get(ORDER_URL, 1234567891))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForOrderUpdate() throws Exception {
        mvc.perform(post(ORDER_UPDATE_URL, 1234567891)
                .param("status", "DELIVERED"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {CUSTOMER_ROLE_NAME})
    @Test
    public void shouldSucceedForOrderAdd() throws Exception {
        mvc.perform(post(ORDER_ADD_URL))
                .andExpect(status().isOk());
    }
}