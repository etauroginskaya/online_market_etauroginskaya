package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.repository.model.User;

public interface EmailService {

    void sendNewUserPassword(String email, String password);

    void sendUpdatedPassword(User user, String password);

    void sendCreateOrderMessage(Long userID, Long orderNumber);

    void sendUpdateStatusOrderMessage(Long userID, Long orderNumber, String newStatus);
}
