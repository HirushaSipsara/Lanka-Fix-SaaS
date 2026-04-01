package lk.wedalk.common.enums;

/**
 * DisputeStatus.java — Dispute Status Enumeration
 *
 * <p>Tracks the status of a dispute raised when a seeker marks a request as "Not Completed":
 * - OPEN: Dispute is active and awaiting admin resolution
 * - RESOLVED: Admin has reviewed and resolved the dispute
 */
public enum DisputeStatus {
    OPEN,
    RESOLVED
}
