package lk.wedalk.reviews.dto;

/**
 * ReviewCreateRequest.java — Create Review DTO
 *
 * This file should contain:
 * - Fields:
 * - Long requestId — @NotNull, the completed request to review
 * - int rating — @Min(1), @Max(5), star rating
 * - String comment — optional review text
 * - Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor
 *
 * Purpose:
 * Used by seekers to leave a review for the worker after job completion.
 */
