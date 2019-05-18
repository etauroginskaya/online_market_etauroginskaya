package com.gmail.etauroginskaya.online_market.service.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
