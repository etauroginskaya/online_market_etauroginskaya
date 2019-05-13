package com.gmail.etauroginskaya.springbootmodule.controller.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ERROR_403_URL;

public class AppAuthenticationDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.warn(String.format("%s was trying to access protected URL: %s",
                    auth.getName(),
                    request.getRequestURI()));
        }
        response.sendRedirect(request.getContextPath() + ERROR_403_URL);
    }
}
