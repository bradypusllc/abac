package com.bradypusllc.abac.core;

import com.bradypusllc.abac.api.DecisionContext;

public class TestDecisionContext implements DecisionContext {

    public TestDecisionContext(Object subject, Object resource, Object environment) {
        this.subject = subject;
        this.resource = resource;
        this.environment = environment;
    }

    private Object subject;
    private Object resource;
    private Object environment;

    @Override
    public Object getSubject() {
        return subject;
    }

    @Override
    public Object getResource() {
        return resource;
    }

    @Override
    public Object getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return "TestDecisionContext{" +
                "subject=" + subject +
                ", resource=" + resource +
                ", environment=" + environment +
                '}';
    }
}
