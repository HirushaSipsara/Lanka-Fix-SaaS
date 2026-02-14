package lk.wedalk.requests.model;

/**
 * ServiceRequest.java — Service Request JPA Entity
 *
 * This file should contain:
 * - @Entity, @Table(name = "service_requests") annotations
 * - Fields:
 * - Long id — @Id, @GeneratedValue
 * - User seeker — @ManyToOne, the user who posted the request
 * - String title — short title (e.g., "Fix leaking pipe")
 * - String description — detailed description of the work needed
 * - String category — service category (e.g., "Plumbing", "Electrical")
 * - String district — location district
 * - String address — specific address or area
 * - double budgetMin — minimum budget (LKR)
 * - double budgetMax — maximum budget (LKR)
 * - RequestStatus status — @Enumerated(EnumType.STRING) — OPEN, ASSIGNED, etc.
 * - User assignedWorker — @ManyToOne (nullable), the worker assigned after
 * accepting quote
 * - LocalDate preferredDate — when the seeker wants the work done
 * - LocalDateTime createdAt
 * - LocalDateTime updatedAt
 * - Lombok: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor
 *
 * Relationships:
 * - @ManyToOne: User (seeker)
 * - @ManyToOne: User (assignedWorker, nullable)
 * - @OneToMany: Quotation (quotes received)
 * - @OneToMany: RequestImage (optional)
 */
