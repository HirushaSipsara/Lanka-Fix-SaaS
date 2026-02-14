/**
 * SeekerDashboard.jsx — Seeker Dashboard Page
 *
 * This file should contain:
 * - Dashboard overview for users with SEEKER role
 * - Sections:
 *     - Welcome message with user's name
 *     - Quick actions: "Create New Request" button
 *     - Summary stats cards:
 *         - Total requests posted
 *         - Open requests (awaiting quotes)
 *         - Assigned/in-progress requests
 *         - Completed requests
 *     - Recent requests list (last 5, using RequestCard)
 *     - Pending actions (quotes to review, reviews to leave)
 * - Fetch data on mount from requestService
 * - Import Loading, ErrorBanner, RequestCard
 *
 * Export: default SeekerDashboard component
 */
