package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.service.CoderService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CoderServiceImpl implements CoderService {

    private static final int ENCODING_STRENGTH = 12;

    @Override
    public String encode(String password) {
        return passwordEncoder().encode(password);
    }

    @Override
    public boolean checkPassword(String existingPassword, String dbPassword) {
        return passwordEncoder().matches(existingPassword, dbPassword);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCODING_STRENGTH);
    }
}
