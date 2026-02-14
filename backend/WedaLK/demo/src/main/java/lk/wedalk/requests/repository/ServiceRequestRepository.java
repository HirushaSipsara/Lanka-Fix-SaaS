package lk.wedalk.requests.repository;

/**
 * ServiceRequestRepository.java — Service Request Data Access Layer
 *
 * This file should contain:
 * - Interface extending JpaRepository<ServiceRequest, Long>
 * - Custom query methods:
 * - List<ServiceRequest> findBySeekerId(Long seekerId)
 * - List<ServiceRequest> findByStatus(RequestStatus status)
 * - List<ServiceRequest> findByDistrict(String district)
 * - List<ServiceRequest> findByCategory(String category)
 * - List<ServiceRequest> findByStatusAndDistrict(RequestStatus status, String
 * district)
 * - List<ServiceRequest> findByAssignedWorkerId(Long workerId)
 *
 * Purpose:
 * Data access for service requests — supports filtering by status, location,
 * and category.
 */
