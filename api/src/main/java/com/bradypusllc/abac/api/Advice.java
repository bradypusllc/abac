package com.bradypusllc.abac.api;

/**
 * Similar to an Obligation, the Advice is defined in XACML 3.0.  It is essential an Obligation that does not have to
 * be met for the decision to complete.  Examples might include:
 *
 *  - deny Patient Joe access to document D + advice: inform Joe that he should call Billing from 9am-4pm for more information
 *  - deny Doctor Charles access to document D + advice: inform Charles why the access was denied
 */
public interface Advice {
}
