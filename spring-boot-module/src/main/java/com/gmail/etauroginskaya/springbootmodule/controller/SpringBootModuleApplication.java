package com.gmail.etauroginskaya.springbootmodule.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.gmail.etauroginskaya",
		exclude = UserDetailsServiceAutoConfiguration.class)
public class SpringBootModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootModuleApplication.class, args);
	}

}
