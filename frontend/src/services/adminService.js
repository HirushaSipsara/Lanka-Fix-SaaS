/**
 * adminService.js — Admin API Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - getAllUsers(filters) → GET /admin/users?role=&status=
 *     - getUserById(id) → GET /admin/users/{id}
 *     - suspendUser(userId, reason) → POST /admin/users/{id}/suspend
 *     - unsuspendUser(userId) → POST /admin/users/{id}/unsuspend
 *     - getPlatformStats() → GET /admin/stats
 *
 * Purpose:
 *   Centralizes all admin API calls.
 *   Used by AdminDashboard, UserManagementPage.
 *
 * Export: { getAllUsers, getUserById, suspendUser, unsuspendUser, getPlatformStats }
 */
