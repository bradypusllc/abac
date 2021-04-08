package com.bradypusllc.abac.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The rule to be evaluated.  This is for transport and storage.  The condition can be of any style that you wish as
 * long as you can serialize it to a String.  The original use case is Spring Expression Language (SpEL).
 *
 * This is the most basic building block of the ABAC system.  It is defined in a Policy which indicates what targets
 * it will be applied to.
 *
 * The conditionEvaluationType determines what RuleEvaluator is used to execute the condition.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Rule {

    private String name;
    private String description;
    private String conditionEvaluationType;
    private String condition;
    private EffectType effect;
}
