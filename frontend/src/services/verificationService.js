/**
 * verificationService.js — Verification API Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - submitVerification(verificationData) → POST /verification/submit
 *     - getMyVerification() → GET /verification/my
 *     - getPendingSubmissions() → GET /verification/pending (admin)
 *     - reviewSubmission(decisionData) → POST /verification/review (admin)
 *
 * Purpose:
 *   Centralizes all verification API calls.
 *   Used by worker verification flow and admin review page.
 *
 * Export: { submitVerification, getMyVerification, getPendingSubmissions, reviewSubmission }
 */
