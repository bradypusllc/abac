package com.bradypusllc.abac.api;

public interface DecisionResult {

    EvaluationContext getEvaluationContext();
    boolean getDecision();
}
