package lk.wedalk.disputes.dto;

/**
 * DisputeCreateRequest.java — Create Dispute DTO
 *
 * This file should contain:
 * - Fields:
 * - Long requestId — @NotNull, the request marked as "Not Completed"
 * - String reason — @NotBlank, seeker's explanation
 * - Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor
 *
 * Purpose:
 * Triggered when a seeker marks a request as "Not Completed".
 * This creates a dispute for admin review.
 * Can be auto-created by the system or manually submitted.
 */
