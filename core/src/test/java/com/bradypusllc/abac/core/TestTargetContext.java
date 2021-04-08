package com.bradypusllc.abac.core;

import com.bradypusllc.abac.api.TargetContext;

public class TestTargetContext implements TargetContext {

    private String executionContext;
    private String target;

    public TestTargetContext(String executionContext, String target) {
        this.executionContext = executionContext;
        this.target = target;
    }

    @Override
    public String getExecutionContext() {
        return executionContext;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
