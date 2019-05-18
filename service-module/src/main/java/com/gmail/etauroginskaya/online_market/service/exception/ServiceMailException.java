package com.gmail.etauroginskaya.online_market.service.exception;

public class ServiceMailException extends RuntimeException {
    public ServiceMailException(String message, Exception e) {
        super(message, e);
    }
}
