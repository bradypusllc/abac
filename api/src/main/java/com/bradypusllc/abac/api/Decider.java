package com.bradypusllc.abac.api;

public interface Decider {

    DecisionResult decide(DecisionRequest decisionRequest);
}
