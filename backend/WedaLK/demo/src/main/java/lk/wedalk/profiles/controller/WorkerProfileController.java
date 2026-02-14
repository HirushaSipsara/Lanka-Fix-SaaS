package lk.wedalk.profiles.controller;

/**
 * WorkerProfileController.java — Worker Profile REST Controller
 *
 * This file should contain:
 * - @RestController, @RequestMapping("/api/profiles") annotations
 * - Inject WorkerProfileService
 * - Endpoints:
 * - POST /api/profiles — Create worker profile
 * - GET /api/profiles/{id} — Get profile by ID
 * - GET /api/profiles/user/{userId} — Get profile by user ID
 * - PUT /api/profiles/{id} — Update worker profile
 * - GET /api/profiles/search?district=&skill= — Search workers
 * - GET /api/profiles/verified — Get all verified workers
 * - All endpoints return ApiResponse<WorkerProfileResponse>
 *
 * Purpose:
 * Exposes worker profile management APIs to the frontend.
 */
