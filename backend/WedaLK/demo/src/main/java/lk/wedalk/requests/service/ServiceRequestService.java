package lk.wedalk.requests.service;

/**
 * ServiceRequestService.java — Service Request Business Logic
 *
 * This file should contain:
 * - @Service annotation
 * - Inject ServiceRequestRepository, UserRepository
 * - Methods:
 * - RequestResponse createRequest(Long seekerId, RequestCreateRequest request)
 * - RequestResponse getRequestById(Long id)
 * - List<RequestResponse> getRequestsBySeeker(Long seekerId)
 * - List<RequestResponse> getOpenRequests()
 * - List<RequestResponse> searchRequests(String district, String category)
 * - RequestResponse updateRequestStatus(Long id, RequestStatus newStatus)
 * - RequestResponse assignWorker(Long requestId, Long workerId)
 * - void cancelRequest(Long requestId, Long seekerId)
 *
 * Purpose:
 * Handles creation, retrieval, search, and status updates for service requests.
 */
