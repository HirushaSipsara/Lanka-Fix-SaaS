/**
 * SubmitQuotePage.jsx — Submit a Quote for a Request (Sprint 2)
 *
 * This file should contain:
 * - Form for workers to submit a quotation on a service request
 * - URL param: request ID
 * - Display request summary at the top (title, category, budget range)
 * - Form fields:
 *     - Price (TextField, type="number") — quoted price in LKR
 *     - Estimated Days (TextField, type="number")
 *     - Message/Proposal (textarea — explain approach, experience)
 * - Form validation
 * - Submit handler: quoteService.createQuote({ requestId, price, estimatedDays, message })
 * - On success: redirect to worker dashboard or request page
 * - Prevent duplicate submissions (check if already quoted)
 *
 * Note: This is a Sprint 2 feature.
 *
 * Export: default SubmitQuotePage component
 */
