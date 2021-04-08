package com.bradypusllc.abac.api;

/**
 * PolicySetSources provide the root PolicySet to the PolicyDecisionPoint.  They DO NOT reduce the PolicySets and
 * Policies based on target statements.  They should return all Policies under a given root.  As such, they should
 * probably implement some kind of caching feature to speed up operations.
 */
public interface PolicySetSource {

    PolicySet getRootPolicySet() throws PolicySetSourceException;
}
