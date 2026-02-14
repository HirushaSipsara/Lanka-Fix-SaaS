/**
 * authService.js — Authentication Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - login(email, password) → POST /auth/login → returns AuthResponse
 *     - register(registerData) → POST /auth/register → returns AuthResponse
 *     - logout() → clear token from localStorage, redirect to /login
 *     - getCurrentUser() → decode JWT or call user endpoint
 *     - isAuthenticated() → check if valid token exists
 *
 * Purpose:
 *   Centralizes all authentication-related API calls.
 *   Used by LoginPage, RegisterPage, AuthContext.
 *
 * Export: { login, register, logout, getCurrentUser, isAuthenticated }
 */
