package com.bradypusllc.abac.core;


import com.bradypusllc.abac.api.EvaluationContext;
import com.bradypusllc.abac.api.EvaluationException;
import com.bradypusllc.abac.api.Policy;
import com.bradypusllc.abac.api.PolicyDecisionPoint;
import com.bradypusllc.abac.api.PolicySet;
import com.bradypusllc.abac.api.Rule;
import com.bradypusllc.abac.api.RuleEvaluator;
import com.bradypusllc.abac.core.evaluator.spel.SpelRuleEvaluator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The original 'basic' implementation of a PDP.  Will traverse the PolicySets from the Root of the EvaluationContext
 * and execute each Rule, combining as required to come to a decision.
 *
 * As a PDP, it is not responsible for retrieving or filtering PolicySets.  That is the job of the PEP.
 */
@Slf4j
public class BasicPolicyDecisionPoint implements PolicyDecisionPoint {

    @Override
    public boolean check(EvaluationContext evaluationContext) {
        // Get the 'root' PolicySet for this EvaluationContext.
        PolicySet rootPolicySet = evaluationContext.getPolicySet();

        // And evaluate it.
        return evaluatePolicySet(rootPolicySet, evaluationContext);
    }

    private boolean evaluatePolicySet(PolicySet policySet, EvaluationContext evaluationContext) {
        boolean rtn = false;

        // Policies are always evaluated before PolicySets as they are more 'proximate'.
        List<Policy> policies = policySet.getPolicies();

        if(policies != null) {
            for (Policy policy : policies) {
                rtn = evaluatePolicy(policy, evaluationContext);
            }
        }

        // Then additional PolicySets can be evaluated.
        List<PolicySet> policySets = policySet.getPolicySets();

        if(policySets != null) {
            for (PolicySet policySet1 : policySets) {
                rtn = evaluatePolicySet(policySet1, evaluationContext);
            }
        }

        return rtn;
    }

    private boolean evaluatePolicy(Policy policy, EvaluationContext evaluationContext) {
        boolean rtn = false;

        // Only Rules left to evaluate.
        List<Rule> rules = policy.getRules();

        if(rules != null) {
            for (Rule rule : rules) {
                rtn = evaluateRule(rule, evaluationContext);
            }
        }

        return rtn;
    }

    private boolean evaluateRule(Rule rule, EvaluationContext evaluationContext) {
        boolean rtn = false;

        try {
            // Load the RuleEvaluator dynamically.
            if ("SPEL".equalsIgnoreCase(rule.getConditionEvaluationType())) {
                RuleEvaluator ruleEvaluator = new SpelRuleEvaluator();
                rtn = ruleEvaluator.evaluate(rule, evaluationContext.getDecisionContext());
            }
        } catch (EvaluationException e) {

        }


        return rtn;
    }
}
