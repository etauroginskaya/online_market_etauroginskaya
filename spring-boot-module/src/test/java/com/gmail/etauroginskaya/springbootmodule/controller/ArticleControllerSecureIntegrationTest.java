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
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.CUSTOMER_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.SALE_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLES_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLES_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLE_DELETE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLE_UPDATE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.COMMENT_DELETE_URL;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootModuleApplication.class, DbConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerSecureIntegrationTest {

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

    @WithMockUser(authorities = {CUSTOMER_ROLE_NAME, SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForArticlesPage() throws Exception {
        mvc.perform(get(ARTICLES_URL))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {CUSTOMER_ROLE_NAME, SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForArticlePage() throws Exception {
        mvc.perform(get(ARTICLE_URL, 1))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForArticleDelete() throws Exception {
        mvc.perform(post(ARTICLE_DELETE_URL, "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ARTICLES_URL.concat(DELETE_SUCCESSFULLY)));
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForCommentDelete() throws Exception {
        mvc.perform(post(COMMENT_DELETE_URL, "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ARTICLES_URL.concat("/1").concat(DELETE_SUCCESSFULLY)));
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForArticleUpdate() throws Exception {
        mvc.perform(post(ARTICLE_UPDATE_URL, "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ARTICLE_URL));
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForNewArticlePage() throws Exception {
        mvc.perform(get(ARTICLES_ADD_URL))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {SALE_ROLE_NAME})
    @Test
    public void shouldSucceedForArticleAdd() throws Exception {
        mvc.perform(post(ARTICLES_ADD_URL))
                .andExpect(status().isOk());
    }
}