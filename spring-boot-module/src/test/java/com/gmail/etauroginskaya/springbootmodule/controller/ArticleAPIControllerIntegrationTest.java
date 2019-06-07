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

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ARTICLES_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ARTICLE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootModuleApplication.class, DbConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleAPIControllerIntegrationTest {

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
    public void shouldGetSucceedWith200ForArticlesAPI() throws Exception {
        mvc.perform(get(API_ARTICLES_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSucceedWith200ForArticlesWithIdAPI() throws Exception {
        mvc.perform(get(API_ARTICLE_URL, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveSucceedWith200ForArticlesAPI() throws Exception {
        mvc.perform(post(API_ARTICLES_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"created\": \"Test created\", " +
                        "\"title\": \"Test title\", " +
                        "\"description\": \"Test description\", " +
                        "\"userDTO\":{\"id\": \"2\"}, " +
                        "\"comments\":[ {" +
                        "\"created\" : \"comment created\", " +
                        "\"description\" : \"comment description\", " +
                        "\"userDTO\" :{\"id\": \"1\"}" +
                        "}]}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteSucceedWith200ForArticlesWithIdAPI() throws Exception {
        mvc.perform(delete(API_ARTICLE_URL, 1))
                .andExpect(status().isOk());
    }
}
