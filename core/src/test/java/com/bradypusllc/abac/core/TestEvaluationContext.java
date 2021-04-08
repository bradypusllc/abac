package com.bradypusllc.abac.core;

import com.bradypusllc.abac.api.DecisionContext;
import com.bradypusllc.abac.api.EvaluationContext;
import com.bradypusllc.abac.api.PolicySet;

public class TestEvaluationContext implements EvaluationContext {

    public TestEvaluationContext(PolicySet policySet, DecisionContext decisionContext) {
        this.policySet = policySet;
        this.decisionContext = decisionContext;
    }

    private PolicySet policySet;
    private DecisionContext decisionContext;

    @Override
    public PolicySet getPolicySet() {
        return policySet;
    }

    @Override
    public DecisionContext getDecisionContext() {
        return decisionContext;
    }

    @Override
    public String toString() {
        return "TestEvaluationContext{" +
                "policySet=" + policySet +
                ", decisionContext=" + decisionContext +
                '}';
    }
}
