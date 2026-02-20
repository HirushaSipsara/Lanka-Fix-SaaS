package lk.wedalk.profiles.dto;

/**
 * WorkerProfileResponse.java — Worker Profile Response DTO
 *
 * <p>This file should contain: - Fields: - Long id - Long userId - String fullName — from User
 * entity - String bio - List<String> skills - String district - List<String> serviceAreas - double
 * hourlyRate - double averageRating - int totalJobsCompleted - VerificationStatus
 * verificationStatus - List<String> portfolioImageUrls — from PortfolioImage entities -
 * LocalDateTime createdAt - Lombok: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor -
 * Static method: fromEntity(WorkerProfile profile) — maps entity to response
 *
 * <p>Purpose: Returned when fetching a worker's profile. Includes aggregated info.
 */
