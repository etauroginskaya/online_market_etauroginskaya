package com.gmail.etauroginskaya.springbootmodule.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.gmail.etauroginskaya",
		exclude = UserDetailsServiceAutoConfiguration.class)
@EntityScan("com.gmail.etauroginskaya.online_market.repository.model")
public class SpringBootModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootModuleApplication.class, args);
	}

}