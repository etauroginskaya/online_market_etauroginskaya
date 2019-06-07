package com.gmail.etauroginskaya.online_market.service;

public interface CoderService {

    String encode(String password);

    boolean checkPassword(String existingPassword, String dbPassword);
}
