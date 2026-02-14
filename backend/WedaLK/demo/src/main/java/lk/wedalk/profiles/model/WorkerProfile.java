package lk.wedalk.profiles.model;

/**
 * WorkerProfile.java — Worker Profile JPA Entity
 *
 * This file should contain:
 * - @Entity, @Table(name = "worker_profiles") annotations
 * - Fields:
 * - Long id — @Id, @GeneratedValue
 * - User user — @OneToOne, the worker this profile belongs to
 * - String bio — short description / about me
 * - List<String> skills — @ElementCollection, list of skills (e.g., "Plumbing",
 * "Electrical")
 * - String district — primary service area
 * - List<String> serviceAreas — @ElementCollection, districts the worker covers
 * - double hourlyRate — optional, worker's hourly rate
 * - double averageRating — computed from reviews (cached)
 * - int totalJobsCompleted — counter of completed jobs
 * - VerificationStatus verificationStatus — NONE, PENDING, APPROVED, REJECTED
 * - LocalDateTime createdAt
 * - LocalDateTime updatedAt
 * - Lombok: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor
 *
 * Relationships:
 * - @OneToOne with User
 * - @OneToMany with PortfolioImage (optional)
 */
