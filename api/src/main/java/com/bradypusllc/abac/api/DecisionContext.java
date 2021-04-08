package com.bradypusllc.abac.api;

/**
 * A DecisionContext represents all of the information collected by the system in preparation to make the Decision.
 */
public interface DecisionContext {

    Object getSubject();
    Object getResource();
    Object getEnvironment();
}
