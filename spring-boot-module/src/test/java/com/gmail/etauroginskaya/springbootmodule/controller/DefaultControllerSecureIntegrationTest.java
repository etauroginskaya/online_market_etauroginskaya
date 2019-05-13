package com.gmail.etauroginskaya.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefaultControllerSecureIntegrationTest {

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

    @Test
    public void shouldSucceedForHomePage() throws Exception {
        mvc.perform(get(HOME_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSucceedForLoginPage() throws Exception {
        mvc.perform(get(LOGIN_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSucceedForReviewsPage() throws Exception {
        mvc.perform(get(REVIEWS_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSucceedForException403Page() throws Exception {
        mvc.perform(get(ERROR_403_URL))
                .andExpect(status().isOk());
    }
}
