package com.bradypusllc.abac.core.evaluator.spel;

import com.bradypusllc.abac.api.Evaluator;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class SpelEvaluator implements Evaluator {

    protected final String EVALUATION_TYPE = "SPEL";

    protected SpelExpressionParser expressionParser;

    public SpelEvaluator() {
        expressionParser = new SpelExpressionParser();
    }

    public SpelEvaluator(SpelExpressionParser spelExpressionParser) {
        this.expressionParser = spelExpressionParser;
    }

    @Override
    public boolean supportsEvaluationType(String evaluationType) {
        return EVALUATION_TYPE.equalsIgnoreCase(evaluationType);
    }

    protected SpelExpression getSpelExpression(String expressionString) {
        return expressionParser.parseRaw(expressionString);
    }

    @Nullable
    <T> T getValue(@NonNull SpelExpression spelExpression, @Nullable Object rootObject, @Nullable Class<T> desiredResultType) throws EvaluationException {
        T result = spelExpression.getValue(rootObject, desiredResultType);

        if(result == null) {
            throw new EvaluationException("An error occurred while evaluating Rule");
        }

        return result;
    }

}
