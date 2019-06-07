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

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ITEMS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ITEM_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootModuleApplication.class, DbConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemAPIControllerIntegrationTest {

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
    public void shouldGetSucceedWith200ForItemsAPI() throws Exception {
        mvc.perform(get(API_ITEMS_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSucceedWith200ForItemWithIdAPI() throws Exception {
        mvc.perform(get(API_ITEM_URL, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveSucceedWith200ForItemsAPI() throws Exception {
        mvc.perform(post(API_ITEMS_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"test name\", " +
                        "\"description\": \"Test description\", " +
                        "\"uniqueNumber\": \"123\", " +
                        "\"price\": \"12.1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteSucceedWith200ForItemsWithIdAPI() throws Exception {
        mvc.perform(delete(API_ITEM_URL, 2))
                .andExpect(status().isOk());
    }
}