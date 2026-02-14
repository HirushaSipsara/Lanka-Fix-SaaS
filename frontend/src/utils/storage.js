/**
 * storage.js — Token & Session Storage Helpers
 *
 * This file should contain:
 * - Functions for managing auth token in localStorage:
 *     - setToken(token)           → localStorage.setItem('token', token)
 *     - getToken()                → localStorage.getItem('token')
 *     - removeToken()             → localStorage.removeItem('token')
 *     - setUser(userObj)          → localStorage.setItem('user', JSON.stringify(userObj))
 *     - getUser()                 → JSON.parse(localStorage.getItem('user'))
 *     - removeUser()              → localStorage.removeItem('user')
 *     - clearAuth()               → removeToken() + removeUser()
 *     - isLoggedIn()              → return !!getToken()
 *
 * Purpose:
 *   Abstracts localStorage access for authentication data.
 *   Used by authService, apiClient, useAuth hook.
 *
 * Security Notes:
 *   - localStorage is vulnerable to XSS — consider httpOnly cookies for production
 *   - Never store sensitive data beyond the JWT token
 *
 * Export: { setToken, getToken, removeToken, setUser, getUser, clearAuth, isLoggedIn }
 */
