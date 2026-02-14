package lk.wedalk.auth.dto;

/**
 * RegisterRequest.java — Registration Request DTO
 *
 * This file should contain:
 * - Fields:
 * - String fullName — user's full name
 * - String email — unique email address
 * - String password — password (min 6 chars)
 * - String phone — Sri Lankan phone number
 * - Role role — SEEKER or WORKER (from common.enums.Role)
 * - String district — user's district in Sri Lanka (optional)
 * - Validation annotations: @NotBlank, @Email, @Size, @NotNull
 * - Lombok annotations: @Data, @NoArgsConstructor, @AllArgsConstructor
 *
 * Purpose:
 * Data Transfer Object for the POST /api/auth/register endpoint.
 */
