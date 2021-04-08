package com.bradypusllc.abac.api;

/**
 * Base interface for all Evaluators.
 */
public interface Evaluator {

    boolean supportsEvaluationType(String evaluationType);
}
