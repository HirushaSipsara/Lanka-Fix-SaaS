package lk.wedalk.requests.repository;

import lk.wedalk.common.enums.RequestStatus;
import lk.wedalk.common.enums.ServiceCategory;
import lk.wedalk.requests.model.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ServiceRequestRepository.java — Service Request Repository
 *
 * Data access layer for service requests.
 */
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findBySeekerId(Long seekerId);

    List<ServiceRequest> findByStatusOrderByCreatedAtDesc(RequestStatus status);

    List<ServiceRequest> findByLocationAreaContainingIgnoreCaseAndStatus(String locationArea, RequestStatus status);

    List<ServiceRequest> findByCategoryAndStatus(ServiceCategory category, RequestStatus status);

    List<ServiceRequest> findByLocationAreaContainingIgnoreCaseAndCategoryAndStatus(
            String locationArea, ServiceCategory category, RequestStatus status);

    /**
     * Paginated browse with optional keyword, category, and location filters.
     * Keyword searches across title and description (case-insensitive).
     */
    @Query("SELECT sr FROM ServiceRequest sr WHERE sr.status = :status " +
            "AND (:keyword IS NULL OR LOWER(sr.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(sr.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:category IS NULL OR sr.category = :category) " +
            "AND (:locationArea IS NULL OR LOWER(sr.locationArea) LIKE LOWER(CONCAT('%', :locationArea, '%')))")
    Page<ServiceRequest> browseOpenRequests(
            @Param("status") RequestStatus status,
            @Param("keyword") String keyword,
            @Param("category") ServiceCategory category,
            @Param("locationArea") String locationArea,
            Pageable pageable);
}
