package lk.wedalk.requests.repository;

import java.util.List;
import lk.wedalk.common.enums.RequestStatus;
import lk.wedalk.common.enums.ServiceCategory;
import lk.wedalk.requests.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ServiceRequestRepository.java — Service Request Repository
 *
 * <p>Data access layer for service requests.
 */
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

  List<ServiceRequest> findBySeekerId(Long seekerId);

  List<ServiceRequest> findByStatusOrderByCreatedAtDesc(RequestStatus status);

  List<ServiceRequest> findByLocationAreaContainingIgnoreCaseAndStatus(
      String locationArea, RequestStatus status);

  List<ServiceRequest> findByCategoryAndStatus(ServiceCategory category, RequestStatus status);

  List<ServiceRequest> findByLocationAreaContainingIgnoreCaseAndCategoryAndStatus(
      String locationArea, ServiceCategory category, RequestStatus status);
}
