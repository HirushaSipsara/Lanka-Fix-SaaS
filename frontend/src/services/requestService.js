/**
 * requestService.js — Service Request API Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - createRequest(requestData) → POST /requests
 *     - getRequestById(id) → GET /requests/{id}
 *     - getMyRequests() → GET /requests/my
 *     - getOpenRequests(filters) → GET /requests/open?district=&category=
 *     - searchRequests(query) → GET /requests/search
 *     - updateRequestStatus(id, status) → PATCH /requests/{id}/status
 *     - cancelRequest(id) → PATCH /requests/{id}/cancel
 *
 * Purpose:
 *   Centralizes all service request API calls.
 *   Used by seeker and worker pages.
 *
 * Export: { createRequest, getRequestById, getMyRequests, getOpenRequests, ... }
 */
