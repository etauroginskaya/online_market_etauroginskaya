package com.gmail.etauroginskaya.springbootmodule.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======
import org.springframework.boot.autoconfigure.domain.EntityScan;
>>>>>>> develop
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.gmail.etauroginskaya",
		exclude = UserDetailsServiceAutoConfiguration.class)
<<<<<<< HEAD
=======
@EntityScan("com.gmail.etauroginskaya.online_market.repository.model")
>>>>>>> develop
public class SpringBootModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootModuleApplication.class, args);
	}

<<<<<<< HEAD
}
=======
}
>>>>>>> develop
