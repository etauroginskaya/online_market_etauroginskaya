package com.gmail.etauroginskaya.springbootmodule.controller.security.handler;

import com.gmail.etauroginskaya.springbootmodule.controller.exception.IllegalAuthenticationStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.ADMIN_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.CUSTOMER_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.RoleConstants.SALE_ROLE_NAME;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ITEMS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ORDERS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.USERS_URL;

public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        logger.info(String.format("User: %s success authenticate and redirect to %s.",
                authentication.getName(), targetUrl));
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            switch (grantedAuthority.getAuthority()) {
                case ADMIN_ROLE_NAME:
                    return USERS_URL;
                case CUSTOMER_ROLE_NAME:
                    return ITEMS_URL;
                case SALE_ROLE_NAME:
                    return ORDERS_URL;
            }
        }
        logger.error(String.format("No authentication success handler for user: %s",
                authentication.getName()));
        throw new IllegalAuthenticationStateException
                (String.format("No authentication success handler for user: %s",
                        authentication.getName()));

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
