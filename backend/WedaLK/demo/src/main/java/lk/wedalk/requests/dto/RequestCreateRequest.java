package lk.wedalk.requests.dto;

/**
 * RequestCreateRequest.java — Create Service Request DTO
 *
 * This file should contain:
 * - Fields:
 * - String title — @NotBlank, request title
 * - String description — @NotBlank, detailed description
 * - String category — @NotBlank, service category
 * - String district — @NotBlank, location district
 * - String address — optional, specific address
 * - double budgetMin — @Positive, minimum budget in LKR
 * - double budgetMax — @Positive, maximum budget in LKR
 * - LocalDate preferredDate — optional, desired completion date
 * - Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor
 *
 * Purpose:
 * Used by seekers when posting a new service request.
 */
