package com.bradypusllc.abac.core.evaluator.spel;

import com.bradypusllc.abac.api.EvaluationException;
import com.bradypusllc.abac.core.TestResource;
import com.bradypusllc.abac.core.TestSubject;
import com.bradypusllc.abac.api.DecisionContext;
import com.bradypusllc.abac.api.EffectType;
import com.bradypusllc.abac.api.Rule;
import com.bradypusllc.abac.core.BasicDecisionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpelRuleEvaluatorTest {

    @Test
    public void test_simpleSpelTest_expectTrue() throws EvaluationException {
        TestSubject testSubject = new TestSubject();
        testSubject.setUsername("bob");

        TestResource testResource = new TestResource();
        testResource.setCreatedBy("bob");

        DecisionContext decisionContext = new BasicDecisionContext(testSubject, testResource, null);
        Rule rule = new Rule(
                "test rule",
                "a test rule that checks that the user is the creator of the resource",
                "SPEL",
                "subject.username == resource.createdBy",
                EffectType.ALLOW
        );

        SpelRuleEvaluator ruleEvaluator = new SpelRuleEvaluator();
        boolean result = ruleEvaluator.evaluate(rule, decisionContext);

        Assertions.assertTrue(result);
    }

    @Test
    public void test_simpleSpelTest_expectFalse() throws EvaluationException {
        TestSubject testSubject = new TestSubject();
        testSubject.setUsername("bob");

        TestResource testResource = new TestResource();
        testResource.setCreatedBy("alice");

        DecisionContext decisionContext = new BasicDecisionContext(testSubject, testResource, null);
        Rule rule = new Rule(
                "test rule",
                "a test rule that checks that the user is the creator of the resource",
                "SPEL",
                "subject.username == resource.createdBy",
                EffectType.ALLOW
        );

        SpelRuleEvaluator ruleEvaluator = new SpelRuleEvaluator();
        boolean result = ruleEvaluator.evaluate(rule, decisionContext);

        Assertions.assertFalse(result);
    }

}
