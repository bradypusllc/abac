package com.bradypusllc.abac.core.evaluator.spel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Slf4j
public class SpelExpressionUtils {

    private SpelExpressionUtils() {
        // Intentionally private
    }

    /**
     * Given a condition to evaluate, determine if the condition will include the 'action' property to evaluate.
     *
     * @param condition the SpEL condition to be evaluated
     *
     * @return true if 'action' is part of the evaluation
     */
    public static boolean isActionCondition(String condition) {
        LOG.trace("condition: {}", condition);

        SpelExpressionParser expressionParser = new SpelExpressionParser();
        SpelExpression expression = expressionParser.parseRaw(condition);
        LOG.trace("ast: {}", expression.toStringAST());

        SpelNode ast = expression.getAST();
        LOG.trace("ast: {}", ast);
        int childCount = ast.getChildCount();
        return isActionCondition(ast, 0, childCount);
    }

    /**
     * Recurse through the SpelNodes looking for any PropertyOrFieldReference that contains the word 'action'.
     *
     * @param spelNode the current 'parent' node to search
     * @param index the starting index of children to recurse to
     * @param childCount the max index of children to recurse to
     *
     * @return true if 'action' was found
     */
    private static boolean isActionCondition(SpelNode spelNode, int index, int childCount) {
        LOG.trace("getChildAt: {}, {}, {}", index, childCount, spelNode);

        if(spelNode instanceof PropertyOrFieldReference) {
            PropertyOrFieldReference propertyOrFieldReference = (PropertyOrFieldReference)spelNode;
            LOG.trace("name: {}", propertyOrFieldReference.getName());

            if("action".equalsIgnoreCase(propertyOrFieldReference.getName())) {
                return true;
            }
        }

        while(index < childCount) {
            SpelNode nextChild = spelNode.getChild(index);
            int nextChildCount = nextChild.getChildCount();

            if(isActionCondition(nextChild, 0, nextChildCount)) {
                return true;
            }

            index++;
        }

        return false;
    }
}
