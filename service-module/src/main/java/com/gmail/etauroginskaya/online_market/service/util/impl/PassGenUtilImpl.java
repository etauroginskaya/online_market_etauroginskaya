package com.gmail.etauroginskaya.online_market.service.util.impl;

import com.gmail.etauroginskaya.online_market.service.util.PassGenUtil;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Collectors;

@Component
public class PassGenUtilImpl implements PassGenUtil {

    @Override
    public String getPassword(int size) {
        String password = new Random()
                .ints(size, 'A', 'z')
                .mapToObj(x -> (char) x)
                .map(String::valueOf)
                .collect(Collectors.joining());
        return password;
    }
}