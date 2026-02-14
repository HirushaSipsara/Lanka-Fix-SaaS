/**
 * QuoteCard.jsx — Quotation Card Component
 *
 * This file should contain:
 * - A card component displaying a worker's quote on a request
 * - Props:
 *     - quote: object — { id, workerName, workerRating, workerVerified,
 *                         price, message, estimatedDays, status, createdAt }
 *     - onAccept: function (optional) — callback when seeker accepts this quote
 *     - onReject: function (optional) — callback when seeker rejects this quote
 *     - showActions: boolean — whether to show accept/reject buttons
 * - Visual elements:
 *     - Worker name with verified badge
 *     - Worker rating stars
 *     - Quoted price (bold, large: "LKR 8,500")
 *     - Estimated days ("~3 days")
 *     - Worker's message/proposal
 *     - Status badge
 *     - Accept/Reject buttons (if showActions && status === 'PENDING')
 *
 * Usage:
 *   <QuoteCard quote={quote} showActions={true} onAccept={handleAccept} />
 *
 * Export: default QuoteCard component
 */
