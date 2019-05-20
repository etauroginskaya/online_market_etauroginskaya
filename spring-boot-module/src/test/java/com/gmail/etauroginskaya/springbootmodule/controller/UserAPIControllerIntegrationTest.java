package com.gmail.etauroginskaya.springbootmodule.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_USER_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootModuleApplication.class, DbConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAPIControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldSaveSucceedWith200ForArticlesAPI() throws Exception {
        mvc.perform(post(API_USER_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\": \"5\", " +
                        "\"surname\": \"Test\", " +
                        "\"name\": \"test\", " +
                        "\"email\": \"test@mail.ru\", " +
                        "\"role\":{\"id\": \"1\", " +
                        "\"name\": \"Administrator\"}}"))
                .andExpect(status().isOk());
    }
}
