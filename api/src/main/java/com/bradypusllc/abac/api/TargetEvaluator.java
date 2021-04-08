package com.bradypusllc.abac.api;

/**
 * A TargetEvaluator is capable of evaluating the current execution context against the target condition.  To properly
 * support a wide variety of different use cases, we allow the targets to be any type of execution context you want.
 * Therefore, the TargetEvaluator implementations must declare the execution type.  This is free form.  However, several
 * obvious solutions are provided for.
 */
public interface TargetEvaluator extends Evaluator {

    boolean supportsExecutionType(String executionType);
    boolean evaluate(TargetContext targetContext) throws EvaluationException;
}
