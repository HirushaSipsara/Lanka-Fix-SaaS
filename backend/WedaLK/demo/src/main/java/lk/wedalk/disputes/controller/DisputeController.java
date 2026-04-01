package lk.wedalk.disputes.controller;

import jakarta.validation.Valid;
import java.util.List;
import lk.wedalk.common.ApiResponse;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.disputes.dto.DisputeCreateRequest;
import lk.wedalk.disputes.dto.DisputeResponse;
import lk.wedalk.disputes.service.DisputeService;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * DisputeController.java — Dispute REST Controller
 *
 * <p>Exposes dispute management APIs for seekers and admins.
 *
 * <p>SCRUM-94: The POST endpoint delegates ownership validation to DisputeService,
 * which verifies the authenticated seeker is the original author of the ServiceRequest.
 */
@RestController
@RequestMapping("/api/disputes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DisputeController {

    private final DisputeService disputeService;
    private final UserRepository userRepository;

    private Long requireCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Authenticated user not found"))
                .getId();
    }

    /**
     * POST /api/disputes — Create a dispute (seeker, when marking "Not Completed").
     *
     * <p>SCRUM-94: Only the seeker who originally posted the service request
     * can submit a dispute. Workers are blocked entirely.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DisputeResponse>> createDispute(
            @Valid @RequestBody DisputeCreateRequest request) {
        DisputeResponse response = disputeService.createDispute(requireCurrentUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Dispute created successfully"));
    }

    /**
     * GET /api/disputes/{id} — Get dispute details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DisputeResponse>> getDisputeById(@PathVariable Long id) {
        DisputeResponse dispute = disputeService.getDisputeById(id);
        return ResponseEntity.ok(ApiResponse.success(dispute, "Dispute retrieved successfully"));
    }

    /**
     * GET /api/disputes/open — Get all open disputes (admin).
     */
    @GetMapping("/open")
    public ResponseEntity<ApiResponse<List<DisputeResponse>>> getOpenDisputes() {
        List<DisputeResponse> disputes = disputeService.getOpenDisputes();
        return ResponseEntity.ok(ApiResponse.success(disputes, "Open disputes retrieved successfully"));
    }

    /**
     * GET /api/disputes/my — Get current user's disputes.
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<DisputeResponse>>> getMyDisputes() {
        List<DisputeResponse> disputes = disputeService.getDisputesBySeeker(requireCurrentUserId());
        return ResponseEntity.ok(ApiResponse.success(disputes, "Your disputes retrieved successfully"));
    }
}
