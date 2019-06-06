package com.gmail.etauroginskaya.springbootmodule.controller.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ERROR_PAGE;

@ControllerAdvice(basePackages = "com.gmail.etauroginskaya")
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception ex, Model model) {
        logger.error("Request {} Thread an Exception {}", req.getRequestURL(), ex);
        ModelAndView mav = new ModelAndView();
        model.addAttribute("url", req.getRequestURL());
        model.addAttribute("exception", ex);
        mav.setViewName(ERROR_PAGE);
        return mav;
    }
}