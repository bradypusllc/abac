package com.bradypusllc.abac.api;

import java.io.IOException;

public class PolicySetSourceException extends IOException {

    public PolicySetSourceException(String message) {
        super(message);
    }

    public PolicySetSourceException(String message, Throwable t) {
        super(message, t);
    }
}
