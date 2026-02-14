/**
 * LoginPage.jsx — User Login Page
 *
 * This file should contain:
 * - Login form with fields:
 *     - Email (TextField)
 *     - Password (TextField, type="password")
 *     - "Login" submit button
 *     - "Don't have an account? Register" link → /register
 * - Form state management (useState)
 * - Form validation (email format, required fields)
 * - Submit handler:
 *     - Call authService.login(email, password)
 *     - On success: store token, redirect to dashboard (based on role)
 *     - On error: display ErrorBanner
 * - Loading state during submission
 * - Import authService from services/authService
 *
 * Purpose:
 *   Authenticates users and redirects them to their role-specific dashboard.
 *
 * Export: default LoginPage component
 */
