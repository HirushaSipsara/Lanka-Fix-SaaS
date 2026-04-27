package lk.wedalk.requests.controller;

import jakarta.validation.Valid;
import java.util.List;
import lk.wedalk.common.ApiResponse;
import lk.wedalk.common.PagedResponse;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.common.enums.ServiceCategory;
import lk.wedalk.requests.dto.AiDescriptionRequest;
import lk.wedalk.requests.dto.AiDescriptionResponse;
import lk.wedalk.requests.dto.RequestCreateRequest;
import lk.wedalk.requests.dto.RequestResponse;
import lk.wedalk.requests.dto.RequestStatusUpdateRequest;
import lk.wedalk.requests.dto.WorkerAssignedJobResponse;
import lk.wedalk.requests.service.AiDescriptionService;
import lk.wedalk.requests.service.ServiceRequestService;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * ServiceRequestController.java — Service Request REST Controller
 *
 * <p>
 * Exposes service request CRUD and search APIs. Authentication is disabled -
 * all endpoints are
 * publicly accessible.
 */
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
public class ServiceRequestController {

  private static final Logger log = LoggerFactory.getLogger(ServiceRequestController.class);

  private final ServiceRequestService serviceRequestService;
  private final AiDescriptionService aiDescriptionService;
  private final UserRepository userRepository;

  private Long requireCurrentUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName();
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Authenticated user not found"))
        .getId();
  }

  @PostMapping
  public ResponseEntity<ApiResponse<RequestResponse>> createRequest(@Valid @RequestBody RequestCreateRequest request) {
    Long userId = requireCurrentUserId();
    log.info("Creating service request: userId={}, title='{}', category={}", userId, request.getTitle(), request.getCategory());
    RequestResponse response = serviceRequestService.createRequest(userId, request);
    log.info("Service request created: id={}", response.getId());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "Service request created successfully"));
  }

  @PostMapping("/ai-description")
  public ResponseEntity<ApiResponse<AiDescriptionResponse>> generateAiDescription(
      @Valid @RequestBody AiDescriptionRequest request) {
    log.info("AI description generation requested for title='{}'", request.getTitle());
    String draft = aiDescriptionService.generateDescription(request);
    log.info("AI description generated successfully");
    return ResponseEntity.ok(ApiResponse.success(
        new AiDescriptionResponse(draft),
        "AI description generated successfully"));
  }

  @GetMapping("/my")
  public ResponseEntity<ApiResponse<List<RequestResponse>>> getMyRequests() {
    List<RequestResponse> requests = serviceRequestService.getMyRequests(requireCurrentUserId());
    return ResponseEntity.ok(ApiResponse.success(requests, "Requests retrieved successfully"));
  }

  @GetMapping("/open")
  public ResponseEntity<ApiResponse<List<RequestResponse>>> getOpenRequests() {
    log.debug("Fetching open requests");
    List<RequestResponse> requests = serviceRequestService.getOpenRequests();
    log.debug("Returned {} open requests", requests.size());
    return ResponseEntity.ok(ApiResponse.success(requests, "Open requests retrieved successfully"));
  }

  @GetMapping("/browse")
  public ResponseEntity<ApiResponse<PagedResponse<RequestResponse>>> browseRequests(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) ServiceCategory category,
      @RequestParam(required = false) String locationArea,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      @RequestParam(defaultValue = "newest") String sortBy) {
    log.debug("Browse requests: keyword='{}', category={}, location='{}', page={}, size={}, sort={}",
        keyword, category, locationArea, page, size, sortBy);
    PagedResponse<RequestResponse> response = serviceRequestService.browseOpenRequests(
        keyword, category, locationArea, page, size, sortBy);
    return ResponseEntity.ok(ApiResponse.success(response, "Browse results retrieved successfully"));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<RequestResponse>> getRequestById(@PathVariable Long id) {
    log.debug("Fetching request by id={}", id);
    RequestResponse request = serviceRequestService.getRequestById(id);
    return ResponseEntity.ok(ApiResponse.success(request, "Request retrieved successfully"));
  }

  @GetMapping("/worker/{workerId}")
  public ResponseEntity<ApiResponse<List<WorkerAssignedJobResponse>>> getAssignedRequestsForWorker(
      @PathVariable Long workerId) {
    List<WorkerAssignedJobResponse> requests = serviceRequestService.getAssignedRequestsForWorker(workerId);
    return ResponseEntity.ok(ApiResponse.success(requests, "Assigned requests retrieved successfully"));
  }

  @GetMapping("/worker/my")
  public ResponseEntity<ApiResponse<List<WorkerAssignedJobResponse>>> getMyAssignedRequests() {
    List<WorkerAssignedJobResponse> requests = serviceRequestService.getAssignedRequestsForWorker(requireCurrentUserId());
    return ResponseEntity.ok(ApiResponse.success(requests, "Assigned requests retrieved successfully"));
  }

  @GetMapping("/search")
  public ResponseEntity<ApiResponse<List<RequestResponse>>> searchRequests(
      @RequestParam(required = false) String locationArea,
      @RequestParam(required = false) ServiceCategory category) {
    List<RequestResponse> requests = serviceRequestService.searchRequests(locationArea, category);
    return ResponseEntity.ok(ApiResponse.success(requests, "Search completed successfully"));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<RequestResponse>> updateRequest(
      @PathVariable Long id,
      @Valid @RequestBody RequestCreateRequest request) {
    log.info("Updating service request: id={}", id);
    RequestResponse response = serviceRequestService.updateRequest(id, requireCurrentUserId(), request);
    log.info("Service request updated: id={}", id);
    return ResponseEntity.ok(ApiResponse.success(response, "Service request updated successfully"));
  }

  @PutMapping("/{requestId}/status")
  public ResponseEntity<ApiResponse<RequestResponse>> updateRequestStatus(
      @PathVariable Long requestId,
      @Valid @RequestBody RequestStatusUpdateRequest request) {
    RequestResponse response = serviceRequestService.updateRequestStatus(requestId, requireCurrentUserId(), request);
    return ResponseEntity.ok(ApiResponse.success(response, "Request status updated successfully"));
  }

  @PatchMapping("/{requestId}/worker-complete")
  public ResponseEntity<ApiResponse<RequestResponse>> workerMarkJobDone(@PathVariable Long requestId) {
    RequestResponse response = serviceRequestService.workerMarkJobDone(requestId, requireCurrentUserId());
    return ResponseEntity.ok(ApiResponse.success(response, "Job marked as done. Awaiting seeker confirmation."));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteRequest(@PathVariable Long id) {
    log.info("Deleting service request: id={}", id);
    serviceRequestService.deleteRequest(id, requireCurrentUserId());
    log.info("Service request deleted: id={}", id);
    return ResponseEntity.ok(ApiResponse.success(null, "Service request deleted successfully"));
  }
}
