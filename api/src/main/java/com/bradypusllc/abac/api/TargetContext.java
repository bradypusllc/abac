package com.bradypusllc.abac.api;

/**
 * A TargetContext holds the information needed to evaluate a Target.  At its most basic, the target should be a
 * method name, REST pattern, action name, or other indicator of when a PolicySet or Policy applies.  The
 * executionContext represents the currently executing location where the evaluation is taking place.  The comparison
 * of the two is what determines if rule checking applies.
 */
public interface TargetContext {

    String getExecutionContext();
    String getTarget();
}
