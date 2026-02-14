package lk.wedalk.config;

/**
 * SecurityConfig.java — Spring Security Configuration (Sprint 2/3)
 *
 * This file should contain:
 * - @Configuration and @EnableWebSecurity annotations
 * - SecurityFilterChain bean defining HTTP security rules
 * - Password encoder bean (BCryptPasswordEncoder)
 * - JWT filter registration (when implementing token-based auth)
 * - Endpoint authorization rules:
 *     - Public: /api/auth/**, /api/health
 *     - Authenticated: /api/requests/**, /api/quotes/**, etc.
 *     - Admin only: /api/admin/**
 * - CSRF disabled for stateless REST API
 * - Session management set to STATELESS
 *
 * Note: This will be implemented in Sprint 2/3 when authentication is added.
 *       For Sprint 1, you may permit all requests.
 */
