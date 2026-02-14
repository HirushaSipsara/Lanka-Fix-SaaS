package lk.wedalk.common.enums;

/**
 * RequestStatus.java — Service Request Status Enumeration
 *
 * This file should contain:
 * - An enum with values: OPEN, ASSIGNED, COMPLETED, NOT_COMPLETED, CANCELLED
 *
 * Purpose:
 *   Tracks the lifecycle of a service request:
 *   - OPEN:          Request is posted and accepting quotes
 *   - ASSIGNED:      A quote has been accepted, worker is assigned
 *   - COMPLETED:     The seeker confirms the job was completed successfully
 *   - NOT_COMPLETED: The seeker reports the job was not completed (triggers dispute)
 *   - CANCELLED:     The request was cancelled by the seeker before assignment
 */
