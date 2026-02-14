/**
 * validators.js — Form Validation Utilities
 *
 * This file should contain:
 * - Reusable validation functions:
 *     - isRequired(value) → returns error message or null
 *     - isValidEmail(email) → regex check for email format
 *     - isMinLength(value, min) → checks minimum string length
 *     - isMaxLength(value, max) → checks maximum string length
 *     - isPositiveNumber(value) → checks if value > 0
 *     - isValidPhone(phone) → regex for Sri Lankan phone numbers (07X-XXXXXXX)
 *     - passwordsMatch(password, confirmPassword) → checks equality
 *     - isWithinBudgetRange(min, max) → checks min <= max
 *     - validateForm(formData, rules) → runs all rules, returns errors object
 *
 * Purpose:
 *   Centralized validation logic reusable across all form pages.
 *   Keeps validation consistent between frontend forms.
 *
 * Export: { isRequired, isValidEmail, isMinLength, ... }
 */
