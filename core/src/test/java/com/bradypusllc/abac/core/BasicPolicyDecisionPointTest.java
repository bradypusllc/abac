package com.bradypusllc.abac.core;

import com.bradypusllc.abac.api.DecisionContext;
import com.bradypusllc.abac.api.EvaluationContext;
import com.bradypusllc.abac.api.PolicyDecisionPoint;
import com.bradypusllc.abac.api.PolicySetSource;
import com.bradypusllc.abac.api.PolicySetSourceException;
import org.junit.jupiter.api.Test;

public class BasicPolicyDecisionPointTest {

    @Test
    public void test_simpleSpelTest_expectTrue() throws PolicySetSourceException {
        TestSubject testSubject = new TestSubject();
        testSubject.setUsername("bob");

        TestResource testResource = new TestResource();
        testResource.setCreatedBy("bob");

        DecisionContext decisionContext = new TestDecisionContext(testSubject, testResource, null);

        PolicySetSource policySetSource = new TestPolicySetSource();

        EvaluationContext evaluationContext = new TestEvaluationContext(policySetSource.getRootPolicySet(), decisionContext);

        PolicyDecisionPoint policyDecisionPoint = new BasicPolicyDecisionPoint();
        policyDecisionPoint.check(evaluationContext);
    }
}
