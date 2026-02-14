package lk.wedalk.users.repository;

/**
 * UserRepository.java — User Data Access Layer
 *
 * This file should contain:
 * - Interface extending JpaRepository<User, Long>
 * - Custom query methods:
 * - Optional<User> findByEmail(String email)
 * - boolean existsByEmail(String email)
 * - List<User> findByRole(Role role)
 * - List<User> findByIsSuspendedTrue()
 *
 * Purpose:
 * Provides CRUD operations and custom queries for the User entity.
 * Spring Data JPA auto-implements these methods based on naming conventions.
 */
