package com.bradypusllc.abac.api;

/**
 * Finally, we can actually evaluate something.
 *
 * A RuleEvaluator is registered to handle a specific EvaluationType.
 */
public interface RuleEvaluator extends Evaluator {

    boolean evaluate(Rule rule, DecisionContext decisionContext) throws EvaluationException;
}
