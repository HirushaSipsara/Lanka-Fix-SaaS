/**
 * reviewService.js — Review API Service
 *
 * Handles review submission and retrieval.
 * SCRUM-94: Only the seeker who originally posted the request can submit a review.
 * The backend enforces ownership validation and returns 403 if violated.
 */

import apiClient from './apiClient';

/**
 * Submit a review for a completed service request.
 * @param {Object} reviewData - { requestId, rating (1-5), comment (optional) }
 * @returns {Promise<Object>} Created review response
 */
export const submitReview = async (reviewData) => {
  const response = await apiClient.post('/reviews', reviewData);
  return response.data.data;
};

/**
 * Get all reviews submitted by the current user (seeker).
 * @returns {Promise<Array>} List of reviews
 */
export const getMyReviews = async () => {
  const response = await apiClient.get('/reviews/my');
  return response.data.data;
};

/**
 * Get all reviews for a specific worker.
 * @param {number} workerId - Worker user ID
 * @returns {Promise<Array>} List of reviews for the worker
 */
export const getReviewsForWorker = async (workerId) => {
  const response = await apiClient.get(`/reviews/worker/${workerId}`);
  return response.data.data;
};
