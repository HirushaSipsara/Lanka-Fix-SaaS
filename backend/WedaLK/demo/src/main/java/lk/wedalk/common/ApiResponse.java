package lk.wedalk.common;

/**
 * ApiResponse.java — Standard API Response Wrapper
 *
 * This file should contain:
 * - A generic class ApiResponse<T> used to wrap all API responses
 * - Fields:
 *     - boolean success   — whether the request was successful
 *     - String message    — a human-readable message (e.g., "Request created successfully")
 *     - T data            — the response payload (can be null for error responses)
 * - Static factory methods for convenience:
 *     - ApiResponse.success(data, message)
 *     - ApiResponse.error(message)
 * - Use Lombok @Data, @AllArgsConstructor, @NoArgsConstructor for brevity
 *
 * Purpose:
 *   Provides a consistent JSON response format across all endpoints.
 *   Example: { "success": true, "message": "OK", "data": { ... } }
 */
