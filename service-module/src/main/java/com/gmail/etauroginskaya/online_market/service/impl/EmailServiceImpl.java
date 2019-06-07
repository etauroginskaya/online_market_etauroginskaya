package com.gmail.etauroginskaya.online_market.service.impl;

<<<<<<< HEAD
=======
import com.gmail.etauroginskaya.online_market.repository.UserRepository;
import com.gmail.etauroginskaya.online_market.repository.model.User;
>>>>>>> develop
import com.gmail.etauroginskaya.online_market.service.EmailService;
import com.gmail.etauroginskaya.online_market.service.exception.ServiceMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
<<<<<<< HEAD
=======
import org.springframework.context.annotation.Lazy;
>>>>>>> develop
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
<<<<<<< HEAD
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private JavaMailSender mailSender;
=======
@Lazy
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
>>>>>>> develop

    @Value("${spring.mail.username}")
    private String username;

<<<<<<< HEAD
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(String to, String subject, String text) {
=======
    public EmailServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    private void sendMessage(String to, String subject, String text) {
>>>>>>> develop
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
<<<<<<< HEAD
            logger.info(String.format("Message to email %s successfully sent", to));
        } catch (MailException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceMailException(String.format("Message to email %s don't sent", to), e);
        }
    }
}
=======
            logger.info("Message: {} to email {} successfully sent", message.getSubject(), to);
        } catch (MailException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceMailException(String.format("Message: %s to email %s don't sent", message.getSubject(), to), e);
        }
    }

    @Override
    public void sendNewUserPassword(String email, String password) {
        String message = String.format("Hello %s!\n The new registered account is assigned a password: %s",
                userRepository.getUserByEmail(email).getName(), password);
        sendMessage(email, "Market account information", message);
    }

    @Override
    public void sendUpdatedPassword(User user, String password) {
        String message = String.format("Hello %s!\n " +
                "Your password has been updated. New password: %s", user.getName(), password);
        sendMessage(user.getEmail(), "Market account information", message);
    }

    @Override
    public void sendCreateOrderMessage(Long userID, Long orderNumber) {
        User user = userRepository.getById(userID);
        String message = String.format("Hello %s!\n Thank you for the created order number %s!", user.getName(), orderNumber);
        sendMessage(user.getEmail(), "Order is accepted", message);
    }

    @Override
    public void sendUpdateStatusOrderMessage(Long userID, Long orderNumber, String newStatus) {
        User user = userRepository.getById(userID);
        String message = String.format("Hello %s!\n Changed order status to %s.",
                user.getName(), newStatus);
        sendMessage(user.getEmail(), String.format("Order %s information", orderNumber), message);
    }
}
>>>>>>> develop
