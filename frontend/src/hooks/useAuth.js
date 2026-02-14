/**
 * useAuth.js — Authentication Custom Hook (Later)
 *
 * This file should contain:
 * - A custom React hook that provides auth state and methods
 * - Uses AuthContext (from context/AuthContext)
 * - Returns:
 *     - user: object | null       — current user info (id, name, email, role)
 *     - isAuthenticated: boolean  — whether user is logged in
 *     - isLoading: boolean        — auth state loading
 *     - login(email, password)    — login and update context
 *     - register(formData)        — register and update context
 *     - logout()                  — logout and clear context
 *     - hasRole(role)             — check if user has specific role
 *
 * Usage:
 *   const { user, isAuthenticated, login, logout, hasRole } = useAuth();
 *   if (hasRole('ADMIN')) { ... }
 *
 * Note: Implement after AuthContext is set up.
 *
 * Export: default useAuth hook
 */
