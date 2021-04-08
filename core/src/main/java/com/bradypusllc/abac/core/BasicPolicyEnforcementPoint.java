package com.bradypusllc.abac.core;

import com.bradypusllc.abac.api.PolicyEnforcementPoint;

public class BasicPolicyEnforcementPoint implements PolicyEnforcementPoint {

    @Override
    public boolean canAccess(Object subject, Object resource, Object execution) {
        return false;
    }
}
