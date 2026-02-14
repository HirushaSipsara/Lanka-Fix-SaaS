/**
 * ErrorBanner.jsx — Error Message Banner Component
 *
 * This file should contain:
 * - A dismissible error/warning banner component
 * - Props:
 *     - message: string — error message to display
 *     - type: 'error' | 'warning' | 'info' | 'success' (default: 'error')
 *     - onClose: function — callback when user dismisses the banner
 * - Visual: colored banner with icon, message text, and close button
 *     - error: red background
 *     - warning: yellow/orange background
 *     - success: green background
 *     - info: blue background
 *
 * Usage:
 *   {error && <ErrorBanner message={error} onClose={() => setError(null)} />}
 *
 * Export: default ErrorBanner component
 */
