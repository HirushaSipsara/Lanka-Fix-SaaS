/**
 * RequestDetailsPage.jsx — Service Request Detail Page
 *
 * This file should contain:
 * - Full details of a single service request
 * - URL param: useParams() to get request ID
 * - Sections:
 *     - Request info: title, description, category, district, address
 *     - Budget range, preferred date, status badge
 *     - Request images (if any)
 *     - Assigned worker info (if status === ASSIGNED)
 *     - Quotes section: list of QuoteCard components
 *         - Accept/reject buttons for seeker (if status === OPEN)
 *     - Action buttons based on status:
 *         - OPEN: Cancel request
 *         - ASSIGNED: Mark as Completed / Mark as Not Completed
 *         - COMPLETED: Leave a review (if not already reviewed)
 * - Fetch request details: requestService.getRequestById(id)
 * - Fetch quotes: quoteService.getQuotesByRequest(id)
 *
 * Export: default RequestDetailsPage component
 */
