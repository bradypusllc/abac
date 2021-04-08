package com.bradypusllc.abac.api;

public interface PolicyEnforcementPoint {

    /**
     * Check if a subject can access a resource.
     *
     * @param subject the Object containing subject attributes
     * @param resource the Object containing resource attributes
     * @param execution the Object containing execution attributes
     *
     * @return true if the subject can access the resource
     */
    boolean canAccess(Object subject, Object resource, Object execution);
}
