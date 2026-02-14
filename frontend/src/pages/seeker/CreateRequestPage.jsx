/**
 * CreateRequestPage.jsx — Create Service Request Page
 *
 * This file should contain:
 * - A form for seekers to post a new service request
 * - Form fields:
 *     - Title (TextField)
 *     - Category (SelectField — Plumbing, Electrical, Painting, Carpentry, etc.)
 *     - Description (textarea)
 *     - District (SelectField — Sri Lankan districts)
 *     - Address (TextField — specific location)
 *     - Budget Min (TextField, type="number")
 *     - Budget Max (TextField, type="number")
 *     - Preferred Date (date picker)
 *     - Upload images (optional, file input)
 * - Form validation before submit
 * - Submit handler: call requestService.createRequest(formData)
 * - On success: redirect to /seeker/my-requests
 * - Loading state, error handling with ErrorBanner
 *
 * Export: default CreateRequestPage component
 */
