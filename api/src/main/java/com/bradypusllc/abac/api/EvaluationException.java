package com.bradypusllc.abac.api;

public class EvaluationException extends Exception {

    public EvaluationException(String message) {
        super(message);
    }

    public EvaluationException(String message, Throwable t) {
        super(message, t);
    }
}
