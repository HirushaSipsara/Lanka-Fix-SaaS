package lk.wedalk.auth.service;

/**
 * AuthService.java — Authentication Business Logic
 *
 * This file should contain:
 * - @Service annotation
 * - Inject UserRepository, PasswordEncoder, JwtTokenProvider (future)
 * - Methods:
 * - AuthResponse register(RegisterRequest request)
 * - Check if email already exists
 * - Hash the password
 * - Create User entity and save
 * - Generate JWT token
 * - Return AuthResponse
 * - AuthResponse login(LoginRequest request)
 * - Find user by email
 * - Verify password
 * - Generate JWT token
 * - Return AuthResponse
 *
 * Purpose:
 * Handles user registration and authentication logic.
 */
