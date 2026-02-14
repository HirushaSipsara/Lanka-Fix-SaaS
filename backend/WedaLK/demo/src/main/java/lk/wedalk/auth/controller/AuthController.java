package lk.wedalk.auth.controller;

/**
 * AuthController.java — Authentication REST Controller
 *
 * This file should contain:
 * - @RestController and @RequestMapping("/api/auth") annotations
 * - POST /api/auth/register — Register a new user (seeker or worker)
 * - POST /api/auth/login — Authenticate user and return JWT token
 * - Inject AuthService for business logic
 * - Return ApiResponse wrapping AuthResponse (containing token + user info)
 *
 * Endpoints:
 * POST /api/auth/register — accepts RegisterRequest DTO
 * POST /api/auth/login — accepts LoginRequest DTO
 */
