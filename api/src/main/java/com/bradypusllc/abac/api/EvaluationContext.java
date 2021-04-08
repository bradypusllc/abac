package com.bradypusllc.abac.api;

/**
 * The EvaluationContext contains all the information required for the PolicyDecisionPoint to do its work.  By the
 * time this is created and passed to the PDP, all user authentication and attribute collection is completed, all
 * resource attributes are provided by the PIPs, the PAP has provided the relevant PolicySets, and the request
 * Environment is established.
 */
public interface EvaluationContext {
    PolicySet getPolicySet();
    DecisionContext getDecisionContext();
}
