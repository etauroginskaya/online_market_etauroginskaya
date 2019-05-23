package com.gmail.etauroginskaya.online_market.service.util;

public interface CoderUtil {

    String encode(String password);

    boolean checkPassword(String existingPassword, String dbPassword);
}
