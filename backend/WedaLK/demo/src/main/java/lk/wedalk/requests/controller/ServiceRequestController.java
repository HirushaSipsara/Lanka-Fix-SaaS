package lk.wedalk.requests.controller;

/**
 * ServiceRequestController.java — Service Request REST Controller
 *
 * This file should contain:
 * - @RestController, @RequestMapping("/api/requests") annotations
 * - Inject ServiceRequestService
 * - Endpoints:
 * - POST /api/requests — Create a new service request (seeker)
 * - GET /api/requests/{id} — Get request by ID
 * - GET /api/requests/my — Get current seeker's requests
 * - GET /api/requests/open — Browse open requests (workers)
 * - GET /api/requests/search?district=&category= — Search requests
 * - PATCH /api/requests/{id}/status — Update request status
 * - PATCH /api/requests/{id}/cancel — Cancel request (seeker)
 * - All endpoints return ApiResponse<RequestResponse>
 *
 * Purpose:
 * Exposes service request CRUD and search APIs.
 */
