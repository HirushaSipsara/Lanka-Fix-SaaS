/**
 * MyRequestsPage.jsx — Seeker's Requests List Page
 *
 * This file should contain:
 * - A list of all service requests posted by the current seeker
 * - Features:
 *     - Filter tabs: All, Open, Assigned, Completed, Cancelled
 *     - Search bar (search by title)
 *     - Request cards grid/list (using RequestCard component)
 *     - Empty state: "You haven't posted any requests yet. Create one!"
 * - Fetch requests on mount: requestService.getMyRequests()
 * - Click on a request card → navigate to /seeker/requests/:id
 * - Loading state, error handling
 *
 * Export: default MyRequestsPage component
 */
