package lk.wedalk.disputes.repository;

/**
 * DisputeRepository.java — Dispute Data Access Layer
 *
 * This file should contain:
 * - Interface extending JpaRepository<Dispute, Long>
 * - Custom query methods:
 * - List<Dispute> findByStatus(DisputeStatus status)
 * - Optional<Dispute> findByRequestId(Long requestId)
 * - List<Dispute> findBySeekerId(Long seekerId)
 * - List<Dispute> findByWorkerId(Long workerId)
 * - List<Dispute> findByStatusOrderByCreatedAtAsc(DisputeStatus status)
 *
 * Purpose:
 * Data access for disputes — supports admin review queue and user dispute
 * history.
 */
