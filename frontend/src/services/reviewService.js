/**
 * reviewService.js — Review API Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - createReview(reviewData) → POST /reviews
 *     - getWorkerReviews(workerId) → GET /reviews/worker/{workerId}
 *     - getMyReviews() → GET /reviews/my
 *
 * Purpose:
 *   Centralizes all review API calls.
 *   Used by RequestDetailsPage (leave review) and WorkerProfilePage (display reviews).
 *
 * Export: { createReview, getWorkerReviews, getMyReviews }
 */
