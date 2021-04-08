package com.bradypusllc.abac.core.evaluator.spel;

import com.bradypusllc.abac.api.EvaluationException;
import lombok.extern.slf4j.Slf4j;
import com.bradypusllc.abac.api.TargetContext;
import com.bradypusllc.abac.api.TargetEvaluator;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

/**
 * The most basic use case for ABAC is to call the PolicyEnforcement right at the point in the code where the action
 * takes place and the resources are available.  To enable that, we provide the Action/SpEL implementation.  This
 * TargetEvaluator is a component of the implementation.
 *
 * A typical form for calling this would be:
 *
 * Policies and PolicySets that do not specify a targetEvaluationType will default to this.
 *
 * If target statements are not defined for a Policy or PolicySet, this implementation will default to 'true'.  To
 * explicitly define 'true'...
 */
@Slf4j
public class SpelTargetActionEvaluator extends SpelEvaluator implements TargetEvaluator {

    protected static final String EXECUTION_TYPE = "ACTION";
    private static final String CONDITION = "executionContext == target";

    private final SpelExpression spelExpression;

    public SpelTargetActionEvaluator() {
        super();
        this.spelExpression = getSpelExpression(CONDITION);
    }

    public SpelTargetActionEvaluator(SpelExpressionParser spelExpressionParser) {
        super(spelExpressionParser);
        this.spelExpression = getSpelExpression(CONDITION);
    }

    @Override
    public boolean supportsExecutionType(String executionType) {
        return EXECUTION_TYPE.equalsIgnoreCase(executionType);
    }

    @Override
    public boolean evaluate(TargetContext targetContext) throws EvaluationException {
        String executionContext = targetContext.getExecutionContext();
        String target = targetContext.getTarget();

        LOG.info("executionContext: {}", executionContext);
        LOG.info("target: {}", target);

        if(StringUtils.isEmpty(target)) {
            LOG.info("Empty target statement defaulting to true");
            return true;
        }

        if(StringUtils.isEmpty(executionContext)) {
            throw new EvaluationException("NULL or empty executionContext is not valid");
        }

        Boolean result = getValue(spelExpression, targetContext, Boolean.class);

        if(result == null) {
            throw new EvaluationException("An error occurred while evaluating Target");
        }

        return result;
    }
}
