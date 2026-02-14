/**
 * BrowseRequestsPage.jsx — Browse Open Service Requests (Worker)
 *
 * This file should contain:
 * - A listing of all OPEN service requests available for quoting
 * - Features:
 *     - Filter by: district, category
 *     - Sort by: newest, budget (high→low), date needed
 *     - Search bar (search by title/keyword)
 *     - Request cards grid (using RequestCard component)
 *     - Click on card → navigate to request details or submit quote
 *     - Empty state: "No open requests matching your filters"
 * - Fetch: requestService.getOpenRequests(filters)
 * - Loading state, error handling
 *
 * Export: default BrowseRequestsPage component
 */
