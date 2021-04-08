package com.bradypusllc.abac.core.evaluator.spel;

import com.bradypusllc.abac.api.EvaluationException;
import com.bradypusllc.abac.api.TargetContext;
import com.bradypusllc.abac.api.TargetEvaluator;
import com.bradypusllc.abac.core.TestTargetContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpelTargetActionEvaluatorTest {

    @Test
    public void test_spelAction_expectTrue() throws EvaluationException {

        TargetContext targetContext = new TestTargetContext("GET_DOCUMENT", "GET_DOCUMENT");

        TargetEvaluator targetEvaluator = new SpelTargetActionEvaluator();
        boolean result = targetEvaluator.evaluate(targetContext);

        Assertions.assertTrue(result);
    }

    @Test
    public void test_spelAction_expectFalse() throws EvaluationException {

        TargetContext targetContext = new TestTargetContext("GET_DOCUMENT", "UPDATE_DOCUMENT");

        TargetEvaluator targetEvaluator = new SpelTargetActionEvaluator();
        boolean result = targetEvaluator.evaluate(targetContext);

        Assertions.assertFalse(result);
    }

    @Test
    public void test_spelAction_expectDefaultEmptyTrue() throws EvaluationException {

        TargetContext targetContext = new TestTargetContext("GET_DOCUMENT", "");

        TargetEvaluator targetEvaluator = new SpelTargetActionEvaluator();
        boolean result = targetEvaluator.evaluate(targetContext);

        Assertions.assertTrue(result);
    }

    @Test
    public void test_spelAction_expectDefaultNullTrue() throws EvaluationException {

        TargetContext targetContext = new TestTargetContext("GET_DOCUMENT", null);

        TargetEvaluator targetEvaluator = new SpelTargetActionEvaluator();
        boolean result = targetEvaluator.evaluate(targetContext);

        Assertions.assertTrue(result);
    }

    @Test
    public void test_spelAction_expectEmptyException() throws EvaluationException {

        TargetContext targetContext = new TestTargetContext("", "GET_DOCUMENT");

        TargetEvaluator targetEvaluator = new SpelTargetActionEvaluator();

        EvaluationException ex = Assertions.assertThrows(EvaluationException.class, () -> {
            targetEvaluator.evaluate(targetContext);
        });

        Assertions.assertNotNull(ex);
        Assertions.assertEquals("NULL or empty executionContext is not valid", ex.getMessage());
    }

    @Test
    public void test_spelAction_expectNullException() throws EvaluationException {

        TargetContext targetContext = new TestTargetContext(null, "GET_DOCUMENT");

        TargetEvaluator targetEvaluator = new SpelTargetActionEvaluator();

        EvaluationException ex = Assertions.assertThrows(EvaluationException.class, () -> {
            targetEvaluator.evaluate(targetContext);
        });

        Assertions.assertNotNull(ex);
        Assertions.assertEquals("NULL or empty executionContext is not valid", ex.getMessage());
    }
}
