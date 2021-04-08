package com.bradypusllc.abac.api;

public class DecisionException extends Exception {

    public DecisionException(String message) {
        super(message);
    }

    public DecisionException(String message, Throwable t) {
        super(message, t);
    }
}
