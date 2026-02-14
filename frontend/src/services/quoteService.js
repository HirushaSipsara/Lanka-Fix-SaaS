/**
 * quoteService.js — Quotation API Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - createQuote(quoteData) → POST /quotes
 *     - getQuotesByRequest(requestId) → GET /quotes/request/{requestId}
 *     - getMyQuotes() → GET /quotes/my
 *     - acceptQuote(quoteId) → PATCH /quotes/{quoteId}/accept
 *     - rejectQuote(quoteId) → PATCH /quotes/{quoteId}/reject
 *
 * Purpose:
 *   Centralizes all quotation API calls.
 *   Used by SubmitQuotePage, CompareQuotesPage, RequestDetailsPage.
 *
 * Export: { createQuote, getQuotesByRequest, getMyQuotes, acceptQuote, rejectQuote }
 */
