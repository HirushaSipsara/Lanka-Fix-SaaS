/**
 * AppRoutes.jsx — Application Route Configuration
 *
 * This file should contain:
 * - Import React Router components (BrowserRouter, Routes, Route)
 * - Import all page components
 * - Import layout components (MainLayout, AdminLayout)
 * - Import ProtectedRoute component (for authenticated routes)
 *
 * Route structure:
 *   / (MainLayout)
 *     ├── /                     → LandingPage
 *     ├── /login                → LoginPage
 *     ├── /register             → RegisterPage
 *     │
 *     ├── /seeker/dashboard     → SeekerDashboard (protected, SEEKER role)
 *     ├── /seeker/create-request → CreateRequestPage
 *     ├── /seeker/my-requests   → MyRequestsPage
 *     ├── /seeker/requests/:id  → RequestDetailsPage
 *     ├── /seeker/compare-quotes/:id → CompareQuotesPage (Sprint 2)
 *     │
 *     ├── /worker/dashboard     → WorkerDashboard (protected, WORKER role)
 *     ├── /worker/browse        → BrowseRequestsPage
 *     ├── /worker/submit-quote/:id → SubmitQuotePage (Sprint 2)
 *     ├── /worker/profile       → WorkerProfilePage
 *     ├── /worker/profile/edit  → EditWorkerProfilePage
 *     │
 *     ├── /admin (AdminLayout, protected, ADMIN role)
 *     │   ├── /admin/dashboard    → AdminDashboard
 *     │   ├── /admin/verification → VerificationReviewPage
 *     │   ├── /admin/disputes     → DisputeReviewPage
 *     │   └── /admin/users        → UserManagementPage
 *     │
 *     └── *                      → NotFound (404)
 *
 * Export: <AppRoutes /> component
 */
