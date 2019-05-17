package com.gmail.etauroginskaya.online_market.service.util.impl;

import com.gmail.etauroginskaya.online_market.service.util.CoderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CoderUtilImpl implements CoderUtil {

    @Override
    public String encode(String password) {
        return passwordEncoder().encode(password);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
