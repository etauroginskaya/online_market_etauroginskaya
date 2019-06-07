package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.service.GeneratorService;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class GeneratorServiceImpl implements GeneratorService {

    @Override
    public String getPassword(int size) {
        String password = new Random()
                .ints(size, 'A', 'z')
                .mapToObj(x -> (char) x)
                .map(String::valueOf)
                .collect(Collectors.joining());
        return password;
    }

    @Override
    public long getNumber(int size) {
        assert size > 0;
        BigInteger integer = new BigInteger(String.valueOf(size));
        return new Random().nextInt((integer.pow(size).subtract(integer.pow(size - 1)))
                .intValue()) + integer.pow(size - 1).intValue();
    }
}