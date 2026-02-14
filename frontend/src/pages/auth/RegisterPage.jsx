/**
 * RegisterPage.jsx — User Registration Page
 *
 * This file should contain:
 * - Registration form with fields:
 *     - Full Name (TextField)
 *     - Email (TextField, type="email")
 *     - Phone Number (TextField)
 *     - Password (TextField, type="password")
 *     - Confirm Password (TextField, type="password")
 *     - Role selector: "I want to..." → "Find workers (Seeker)" / "Find work (Worker)"
 *     - District (SelectField with Sri Lankan districts)
 *     - "Register" submit button
 *     - "Already have an account? Login" link → /login
 * - Form validation:
 *     - All required fields filled
 *     - Valid email format
 *     - Password min 6 chars
 *     - Passwords match
 * - Submit handler:
 *     - Call authService.register(formData)
 *     - On success: store token, redirect to dashboard
 *     - On error: display ErrorBanner
 *
 * Export: default RegisterPage component
 */
