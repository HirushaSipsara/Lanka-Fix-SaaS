package lk.wedalk.auth.dto;

/**
 * AuthResponse.java — Authentication Response DTO
 *
 * This file should contain:
 * - Fields:
 * - String token — JWT access token
 * - String tokenType — "Bearer" (default)
 * - Long userId — authenticated user's ID
 * - String fullName — user's display name
 * - String email — user's email
 * - Role role — user's role (SEEKER, WORKER, ADMIN)
 * - Lombok
 * annotations: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor
 *
 * Purpose:
 * Returned after successful login/registration with the JWT token
 * and basic user info for the frontend to store and use.
 */
