package com.gmail.etauroginskaya.online_market.service;

public interface EmailService {

    void sendMessage(String to, String subject, String text);
}
