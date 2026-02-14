/**
 * CompareQuotesPage.jsx — Compare Quotes Side-by-Side (Sprint 2)
 *
 * This file should contain:
 * - A comparison view for all quotes received on a request
 * - URL param: request ID
 * - Features:
 *     - Side-by-side or table comparison of quotes
 *     - Columns: Worker Name, Rating, Verified, Price, Est. Days, Message
 *     - Sort by: price (low→high), rating (high→low), days (fastest)
 *     - Highlight best value (lowest price), top rated, fastest
 *     - Accept/reject buttons for each quote
 * - Fetch quotes: quoteService.getQuotesByRequest(id)
 *
 * Note: This is a Sprint 2 feature.
 *
 * Export: default CompareQuotesPage component
 */
