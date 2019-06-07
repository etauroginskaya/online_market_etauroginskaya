package com.gmail.etauroginskaya.springbootmodule.controller.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ERROR_PAGE;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest req, Exception ex) {
        logger.error("Request {} Thread an Exception {}", req.getRequestURL(), ex);
        req.setAttribute("url", req.getRequestURL());
        req.setAttribute("exception", ex);
        return ERROR_PAGE;
    }
}