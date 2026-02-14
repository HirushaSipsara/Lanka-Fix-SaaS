/**
 * disputeService.js — Dispute API Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - createDispute(disputeData) → POST /disputes
 *     - getDisputeById(id) → GET /disputes/{id}
 *     - getOpenDisputes() → GET /disputes/open (admin)
 *     - resolveDispute(id, resolutionData) → POST /disputes/{id}/resolve (admin)
 *
 * Purpose:
 *   Centralizes all dispute API calls.
 *   Used by RequestDetailsPage (raise dispute) and admin DisputeReviewPage.
 *
 * Export: { createDispute, getDisputeById, getOpenDisputes, resolveDispute }
 */
