package com.bradypusllc.abac.core.evaluator.spel;

import com.bradypusllc.abac.api.EvaluationException;
import com.bradypusllc.abac.api.Rule;
import lombok.extern.slf4j.Slf4j;
import com.bradypusllc.abac.api.DecisionContext;
import com.bradypusllc.abac.api.EffectType;
import com.bradypusllc.abac.api.RuleEvaluator;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * The RuleEvaluator for all SpEL Expressions.
 */
@Slf4j
public class SpelRuleEvaluator extends SpelEvaluator implements RuleEvaluator {

    public SpelRuleEvaluator() {
        super();
    }

    public SpelRuleEvaluator(SpelExpressionParser spelExpressionParser) {
        super(spelExpressionParser);
    }

    @Override
    public boolean evaluate(Rule rule, DecisionContext decisionContext) throws EvaluationException {
        try {
            String condition = rule.getCondition();
            LOG.info("condition: {}", condition);

            SpelExpression expression = getSpelExpression(condition);

            LOG.info("expression: {}", expression);
            LOG.info("decisionContext: {}", decisionContext);

            Boolean result = getValue(expression, decisionContext, Boolean.class);

            if(result == null) {
                throw new EvaluationException("An error occurred while evaluating Rule");
            }

            if(result && rule.getEffect() == EffectType.ALLOW) {
                return true;
            }
        } catch(org.springframework.expression.EvaluationException e) {
            throw new EvaluationException("An error occurred while evaluating Rule", e);
        }

        return false;
    }
}
