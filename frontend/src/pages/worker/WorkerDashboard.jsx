/**
 * WorkerDashboard.jsx — Worker Dashboard Page
 *
 * This file should contain:
 * - Dashboard overview for users with WORKER role
 * - Sections:
 *     - Welcome message with worker's name
 *     - Verification status banner (if NONE or PENDING: prompt to verify)
 *     - Quick actions: "Browse Requests", "Edit Profile"
 *     - Summary stats cards:
 *         - Total quotes submitted
 *         - Accepted quotes
 *         - Jobs completed
 *         - Average rating
 *     - Active jobs (assigned requests)
 *     - Recent quote submissions
 * - Fetch data from profileService, quoteService
 * - Import Loading, ErrorBanner, RequestCard
 *
 * Export: default WorkerDashboard component
 */
