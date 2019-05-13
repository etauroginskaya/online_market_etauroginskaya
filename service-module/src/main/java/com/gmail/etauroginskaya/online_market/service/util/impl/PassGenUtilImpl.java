package com.gmail.etauroginskaya.online_market.service.util.impl;

import com.gmail.etauroginskaya.online_market.service.EmailService;
import com.gmail.etauroginskaya.online_market.service.util.PassGenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Collectors;

@Component
public class PassGenUtilImpl implements PassGenUtil {

    private final EmailService emailService;

    public PassGenUtilImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public String getPassword(int size, String email) {
        String password = new Random()
                .ints(size, 'A', 'z')
                .mapToObj(x -> (char) x)
                .map(String::valueOf)
                .collect(Collectors.joining());
        emailService.sendMessage(email, password);
        String encryptedPassword = passwordEncoder().encode(password);
        return encryptedPassword;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
