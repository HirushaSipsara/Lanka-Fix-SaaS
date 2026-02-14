package lk.wedalk.verification.controller;

/**
 * VerificationController.java — Verification REST Controller
 *
 * This file should contain:
 * - @RestController, @RequestMapping("/api/verification") annotations
 * - Inject VerificationService
 * - Endpoints:
 * - POST /api/verification/submit — Submit verification (worker)
 * - GET /api/verification/my — Get current worker's submission status
 * - GET /api/verification/pending — Get pending submissions (admin)
 * - POST /api/verification/review — Approve/reject a submission (admin)
 * - All endpoints return ApiResponse
 *
 * Purpose:
 * Exposes verification submission and admin review APIs.
 */
