package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/login"})
    public String getLoginPage() {
        return "/login";
    }
}
