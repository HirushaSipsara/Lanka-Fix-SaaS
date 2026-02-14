package lk.wedalk.users.model;

/**
 * User.java — User JPA Entity
 *
 * This file should contain:
 * - @Entity, @Table(name = "users") annotations
 * - Fields:
 * - Long id — @Id, @GeneratedValue (primary key)
 * - String fullName — user's full name
 * - String email — unique email, used for login
 * - String password — hashed password (BCrypt)
 * - String phone — contact number
 * - String district — district in Sri Lanka (e.g., Colombo, Kandy)
 * - Role role — @Enumerated(EnumType.STRING) — SEEKER, WORKER, or ADMIN
 * - boolean isSuspended — whether the account is suspended by admin
 * - LocalDateTime createdAt — @CreationTimestamp
 * - LocalDateTime updatedAt — @UpdateTimestamp
 * - Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder
 *
 * Relationships:
 * - One-to-One: WorkerProfile (if role == WORKER)
 * - One-to-Many: ServiceRequest (as seeker)
 * - One-to-Many: Quotation (as worker)
 * - One-to-Many: Review (as reviewer and reviewee)
 */
