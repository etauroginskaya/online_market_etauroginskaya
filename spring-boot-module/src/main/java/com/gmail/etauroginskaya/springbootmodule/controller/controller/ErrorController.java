package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ERROR_403_URL;

@Controller
public class ErrorController {
    @GetMapping(ERROR_403_URL)
    public ModelAndView accesssDenied(Principal user) {
        ModelAndView model = new ModelAndView();
        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }
        model.setViewName("/error/403");
        return model;
    }
}
