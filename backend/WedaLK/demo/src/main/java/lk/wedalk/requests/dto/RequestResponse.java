package lk.wedalk.requests.dto;

/**
 * RequestResponse.java — Service Request Response DTO
 *
 * This file should contain:
 * - Fields:
 * - Long id
 * - String seekerName — from User entity
 * - Long seekerId
 * - String title
 * - String description
 * - String category
 * - String district
 * - String address
 * - double budgetMin
 * - double budgetMax
 * - RequestStatus status
 * - String assignedWorkerName — nullable
 * - Long assignedWorkerId — nullable
 * - LocalDate preferredDate
 * - int quoteCount — number of quotes received
 * - List<String> imageUrls — from RequestImage entities
 * - LocalDateTime createdAt
 * - Lombok: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor
 * - Static method: fromEntity(ServiceRequest request)
 *
 * Purpose:
 * Returned when fetching service request details.
 */
