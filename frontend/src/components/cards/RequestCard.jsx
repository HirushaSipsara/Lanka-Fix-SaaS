/**
 * RequestCard.jsx — Service Request Card Component
 *
 * This file should contain:
 * - A card component displaying a service request summary
 * - Props:
 *     - request: object — { id, title, category, district, budgetMin, budgetMax,
 *                           status, quoteCount, createdAt, seekerName }
 *     - onClick: function — navigate to request details
 * - Visual elements:
 *     - Category badge/tag
 *     - Title (bold)
 *     - District with location icon
 *     - Budget range (e.g., "LKR 5,000 - 10,000")
 *     - Status badge (colored: green=OPEN, blue=ASSIGNED, etc.)
 *     - Quote count (e.g., "3 quotes received")
 *     - Posted date (relative: "2 hours ago")
 *
 * Usage:
 *   <RequestCard request={request} onClick={() => navigate(`/requests/${request.id}`)} />
 *
 * Export: default RequestCard component
 */
