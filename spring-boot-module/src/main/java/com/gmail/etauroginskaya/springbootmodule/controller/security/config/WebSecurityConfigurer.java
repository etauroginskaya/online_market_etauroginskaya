package com.gmail.etauroginskaya.springbootmodule.controller.security.config;

import com.gmail.etauroginskaya.springbootmodule.controller.security.handler.AppAuthenticationDeniedHandler;
import com.gmail.etauroginskaya.springbootmodule.controller.security.handler.AppAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.ADMIN_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.CUSTOMER_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.SALE_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.*;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfigurer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(USERS_URL, USERS_ADD_URL, USERS_UPDATE_URL, USERS_DELETE_URL,
                        REVIEWS_UPDATE_URL, REVIEWS_DELETE_URL).hasAuthority(ADMIN_ROLE_NAME)
                .antMatchers(PROFILE_URL).hasAuthority(CUSTOMER_ROLE_NAME)
                .antMatchers(ARTICLE_DELETE_URL, ARTICLE_UPDATE_URL, ARTICLES_ADD_URL, COMMENT_DELETE_URL,
                        ITEMS_URL, ITEM_URL, ITEM_DELETE_URL, ITEM_COPY_URL, ITEM_ADD_URL).hasAuthority(SALE_ROLE_NAME)
                .antMatchers(ARTICLES_URL, ARTICLE_URL).hasAnyAuthority(CUSTOMER_ROLE_NAME, SALE_ROLE_NAME)
                .antMatchers(HOME_URL, ERROR_403_URL, LOGIN_URL, REVIEWS_URL).permitAll()
                .and()
                .formLogin()
                .loginPage(LOGIN_URL)
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(loginAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler loginAccessDeniedHandler() {
        return new AppAuthenticationDeniedHandler();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }
}
