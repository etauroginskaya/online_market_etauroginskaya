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

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.SALE_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEMS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_COPY_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_DELETE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEM_URL;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootModuleApplication.class, DbConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerSecureIntegrationTest {

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

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForItemsPage() throws Exception {
        mvc.perform(get(ITEMS_URL))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForItem() throws Exception {
        mvc.perform(get(ITEM_URL, "2"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForItemCopy() throws Exception {
        mvc.perform(post(ITEM_COPY_URL, "2"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForItemDelete() throws Exception {
        mvc.perform(post(ITEM_DELETE_URL, "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ITEMS_URL.concat(DELETE_SUCCESSFULLY)));
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForItemAdd() throws Exception {
        mvc.perform(post(ITEM_ADD_URL))
                .andExpect(status().isOk());
    }
}