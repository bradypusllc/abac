package com.bradypusllc.abac.core;

import com.bradypusllc.abac.api.CombiningMethod;
import com.bradypusllc.abac.api.Policy;
import com.bradypusllc.abac.api.PolicySet;
import com.bradypusllc.abac.api.PolicySetSource;
import com.bradypusllc.abac.api.Rule;

import java.util.Collections;

public class TestPolicySetSource implements PolicySetSource {

    @Override
    public PolicySet getRootPolicySet() {

        PolicySet rootPolicySet = new PolicySet();

        rootPolicySet.setDescription("root-policy-set");
        rootPolicySet.setTargetEvaluationType("SPEL"); // All roots still need target evaluation
        rootPolicySet.setTarget(""); // But should probably target all

        // Typically, each sub-PolicySet will be a complete evaluation on its own.  And we want the first one that
        // says allow to pass.
        rootPolicySet.setCombiningMethod(CombiningMethod.FIRST_ALLOW);

        // Add the 'admin-all' PolicySet to the root.
        rootPolicySet.setPolicySets(Collections.singletonList(adminAllowAllPolicySet()));

        return rootPolicySet;
    }

    private PolicySet adminAllowAllPolicySet() {
        PolicySet policySet = new PolicySet();

        policySet.setDescription("A PolicySet that will encompass all targets and check that the subject is an administrator");

        // Just like the root, this PolicySet will target all.
        policySet.setTargetEvaluationType("SPEL");
        policySet.setTarget("");

        // There is only one Rule.  However it must be in a Policy.
        policySet.setPolicies(Collections.singletonList(adminAllowAllPolicy()));

        return  policySet;
    }

    private Policy adminAllowAllPolicy() {
        Policy policy = new Policy();

        policy.setDescription("");

        // Just like the root, this PolicySet will target all.  Also, even though there is a single Rule, we will
        // still specify the CombiningMethod to FIRST_ALLOW.
        policy.setTargetEvaluationType("SPEL");
        policy.setTarget("");
        policy.setCombiningMethod(CombiningMethod.FIRST_ALLOW);

        // The only Rule
        Rule rule = new Rule();

        rule.setName("allow admins");
        rule.setDescription("Allow ADMINs to do anything they want");
        rule.setConditionEvaluationType("SPEL");
        rule.setCondition("true");

        policy.setRules(Collections.singletonList(rule));

        return policy;
    }
}
