package lk.wedalk.requests.service;

import lk.wedalk.common.enums.RequestStatus;
import lk.wedalk.common.enums.Role;
import lk.wedalk.common.enums.ServiceCategory;
import lk.wedalk.common.enums.UrgencyLevel;
import lk.wedalk.common.exceptions.BadRequestException;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.common.exceptions.UnauthorizedException;
import lk.wedalk.requests.dto.RequestCreateRequest;
import lk.wedalk.requests.dto.RequestResponse;
import lk.wedalk.requests.model.ServiceRequest;
import lk.wedalk.requests.repository.ServiceRequestRepository;
import lk.wedalk.users.model.User;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ServiceRequestService.java — Service Request Business Logic
 *
 * Handles creation, retrieval, and search for service requests.
 */
@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public RequestResponse createRequest(Long seekerId, RequestCreateRequest request) {
        // Validate seeker exists and has ROLE_SEEKER
        User seeker = userRepository.findById(seekerId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (seeker.getRole() != Role.SEEKER) {
            throw new UnauthorizedException("Only seekers can create service requests");
        }

        // Set default urgency to MEDIUM if not provided
        UrgencyLevel urgency = request.getUrgency() != null ? request.getUrgency() : UrgencyLevel.MEDIUM;

        // Create service request
        ServiceRequest serviceRequest = ServiceRequest.builder()
                .description(request.getDescription())
                .category(request.getCategory())
                .locationArea(request.getLocationArea())
                .urgency(urgency)
                .status(RequestStatus.OPEN)
                .seeker(seeker)
                .build();

        ServiceRequest saved = serviceRequestRepository.save(serviceRequest);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<RequestResponse> getMyRequests(Long seekerId) {
        List<ServiceRequest> requests = serviceRequestRepository.findBySeekerId(seekerId);
        return requests.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RequestResponse> getOpenRequests() {
        List<ServiceRequest> requests = serviceRequestRepository.findByStatusOrderByCreatedAtDesc(RequestStatus.OPEN);
        return requests.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RequestResponse getRequestById(Long requestId) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Service request not found"));
        return mapToResponse(request);
    }

    @Transactional(readOnly = true)
    public List<RequestResponse> searchRequests(String locationArea, ServiceCategory category) {
        List<ServiceRequest> requests;

        if (locationArea != null && category != null) {
            requests = serviceRequestRepository.findByLocationAreaContainingIgnoreCaseAndCategoryAndStatus(
                    locationArea, category, RequestStatus.OPEN);
        } else if (locationArea != null) {
            requests = serviceRequestRepository.findByLocationAreaContainingIgnoreCaseAndStatus(
                    locationArea, RequestStatus.OPEN);
        } else if (category != null) {
            requests = serviceRequestRepository.findByCategoryAndStatus(category, RequestStatus.OPEN);
        } else {
            requests = serviceRequestRepository.findByStatusOrderByCreatedAtDesc(RequestStatus.OPEN);
        }

        return requests.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RequestResponse mapToResponse(ServiceRequest request) {
        return RequestResponse.builder()
                .id(request.getId())
                .description(request.getDescription())
                .category(request.getCategory())
                .locationArea(request.getLocationArea())
                .urgency(request.getUrgency())
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .seekerId(request.getSeeker().getId())
                .seekerName(request.getSeeker().getFullName())
                .seekerPhone(request.getSeeker().getPhone())
                .build();
    }
}
