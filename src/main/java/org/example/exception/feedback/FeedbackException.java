package org.example.exception.feedback;

import org.example.exception.reading.ReadingException;

public class FeedbackException extends ReadingException {

    public FeedbackException(String errorMessage) {
        super(errorMessage);
    }
}
