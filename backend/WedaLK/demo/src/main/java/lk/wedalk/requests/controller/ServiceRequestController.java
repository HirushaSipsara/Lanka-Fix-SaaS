package lk.wedalk.requests.controller;

import jakarta.validation.Valid;
import lk.wedalk.auth.security.CustomUserDetails;
import lk.wedalk.common.ApiResponse;
import lk.wedalk.common.enums.ServiceCategory;
import lk.wedalk.requests.dto.RequestCreateRequest;
import lk.wedalk.requests.dto.RequestResponse;
import lk.wedalk.requests.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ServiceRequestController.java — Service Request REST Controller
 *
 * Exposes service request CRUD and search APIs.
 */
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @PostMapping
    @PreAuthorize("hasRole('SEEKER')")
    public ResponseEntity<ApiResponse<RequestResponse>> createRequest(
            @Valid @RequestBody RequestCreateRequest request,
            Authentication authentication) {
        Long seekerId = getCurrentUserId(authentication);
        RequestResponse response = serviceRequestService.createRequest(seekerId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Service request created successfully"));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('SEEKER')")
    public ResponseEntity<ApiResponse<List<RequestResponse>>> getMyRequests(Authentication authentication) {
        Long seekerId = getCurrentUserId(authentication);
        List<RequestResponse> requests = serviceRequestService.getMyRequests(seekerId);
        return ResponseEntity.ok(ApiResponse.success(requests, "Requests retrieved successfully"));
    }

    @GetMapping("/open")
    @PreAuthorize("hasAnyRole('SEEKER', 'WORKER')")
    public ResponseEntity<ApiResponse<List<RequestResponse>>> getOpenRequests() {
        List<RequestResponse> requests = serviceRequestService.getOpenRequests();
        return ResponseEntity.ok(ApiResponse.success(requests, "Open requests retrieved successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<RequestResponse>> getRequestById(@PathVariable Long id) {
        RequestResponse request = serviceRequestService.getRequestById(id);
        return ResponseEntity.ok(ApiResponse.success(request, "Request retrieved successfully"));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('SEEKER', 'WORKER')")
    public ResponseEntity<ApiResponse<List<RequestResponse>>> searchRequests(
            @RequestParam(required = false) String locationArea,
            @RequestParam(required = false) ServiceCategory category) {
        List<RequestResponse> requests = serviceRequestService.searchRequests(locationArea, category);
        return ResponseEntity.ok(ApiResponse.success(requests, "Search completed successfully"));
    }

    /**
     * Helper method to extract user ID from authentication principal
     */
    private Long getCurrentUserId(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
