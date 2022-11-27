package com.example.socialnetworkgui.service;

/**
 * Service exception used at the service layer
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
