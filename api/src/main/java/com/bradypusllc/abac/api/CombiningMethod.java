package com.bradypusllc.abac.api;

public enum CombiningMethod {
    FIRST_DENY,  // A single deny will cause a false decision
    FIRST_ALLOW, // A single allow will cause a true decision
}
