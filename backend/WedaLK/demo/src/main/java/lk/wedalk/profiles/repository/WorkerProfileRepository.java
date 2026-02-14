package lk.wedalk.profiles.repository;

/**
 * WorkerProfileRepository.java — Worker Profile Data Access Layer
 *
 * This file should contain:
 * - Interface extending JpaRepository<WorkerProfile, Long>
 * - Custom query methods:
 * - Optional<WorkerProfile> findByUserId(Long userId)
 * - List<WorkerProfile> findByDistrict(String district)
 * - List<WorkerProfile> findBySkillsContaining(String skill)
 * - List<WorkerProfile> findByVerificationStatus(VerificationStatus status)
 *
 * Purpose:
 * Data access for worker profiles — supports search by location, skill, and
 * verification status.
 */
