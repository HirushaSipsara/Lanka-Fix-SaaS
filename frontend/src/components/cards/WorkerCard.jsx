/**
 * WorkerCard.jsx — Worker Profile Card Component
 *
 * This file should contain:
 * - A card component displaying a worker's profile summary
 * - Props:
 *     - worker: object — { id, fullName, bio, skills, district, averageRating,
 *                          totalJobsCompleted, verificationStatus, hourlyRate }
 *     - onClick: function — navigate to worker profile
 * - Visual elements:
 *     - Worker name
 *     - Verified badge (✓) if verificationStatus === 'APPROVED'
 *     - Star rating display (e.g., ★★★★☆ 4.2)
 *     - Skills tags (e.g., "Plumbing", "Electrical")
 *     - District / service area
 *     - Jobs completed count
 *     - Hourly rate (if set)
 *     - Short bio excerpt
 *
 * Usage:
 *   <WorkerCard worker={worker} onClick={() => navigate(`/workers/${worker.id}`)} />
 *
 * Export: default WorkerCard component
 */
