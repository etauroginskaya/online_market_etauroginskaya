package com.gmail.etauroginskaya.springbootmodule.controller.exception;

public class IllegalAuthenticationStateException extends RuntimeException {
    public IllegalAuthenticationStateException(String message) {
        super(message);
    }
}
