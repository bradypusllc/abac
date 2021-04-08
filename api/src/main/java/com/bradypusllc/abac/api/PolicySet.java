package com.bradypusllc.abac.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * A PolicySet represents one or more Policy or PolicySets that can be applied to a given target.  Both the PolicySets
 * and Policies are optional within a given PolicySet.  There is no limit to depth.
 *
 * The PolicySet is the top-level element in the ABAC system.  There should be a 'root' element that begins the Policy
 * hierarchy.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PolicySet {

    private String description;
    private String targetEvaluationType;
    private String target;
    private List<PolicySet> policySets;
    private List<Policy> policies;
    private CombiningMethod combiningMethod;
}
