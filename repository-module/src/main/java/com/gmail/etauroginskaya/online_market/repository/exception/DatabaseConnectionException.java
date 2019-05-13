package com.gmail.etauroginskaya.online_market.repository.exception;

public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(String message, Throwable e) {
        super(message, e);
    }
}
