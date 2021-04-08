package com.bradypusllc.abac.api;

/**
 * Responsible for making a decision, the PDP takes the EvaluationContext and executes all the relevant Rules to build
 * a final decision.
 */
public interface PolicyDecisionPoint {

    boolean check(EvaluationContext evaluationContext);
}
