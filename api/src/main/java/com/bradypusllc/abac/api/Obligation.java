package com.bradypusllc.abac.api;

/**
 * Introduced in XACML 3.0, an Obligation is an additional action that MUST be fulfilled.  Several examples are:
 *
 *   - deny Billing Processor Alice access to document D + obligation: email her manager, Bob, to let him know Alice tried to access document D.
 *   - deny Doctor Charles the right to view medical record + obligation: tell Doctor Charles he can "break the glass" to access the medical record.
 *   - allow Patient Joe to view Document D but first watermark the document before returning it to Joe
 *
 * This interface is a placeholder.  We do not intend on implementing it any time soon.
 */
public interface Obligation {
}
