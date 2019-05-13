package com.gmail.etauroginskaya.online_market.repository.exception;

public class DocumentReadException extends RuntimeException {

    public DocumentReadException(String message, Throwable e) {
        super(message, e);
    }
}
