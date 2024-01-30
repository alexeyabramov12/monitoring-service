package org.example.exception.reading;

public class ReadingException extends RuntimeException{

    public ReadingException(String errorMessage) {
        super(errorMessage);
    }
}
