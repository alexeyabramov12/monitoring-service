package org.example.exception.auth;

public class AuthException extends RuntimeException{

    public AuthException(String errorMessage) {
        super(errorMessage);
    }

}
