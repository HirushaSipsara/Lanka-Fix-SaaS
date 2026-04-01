package lk.wedalk.reviews.controller;

import jakarta.validation.Valid;
import java.util.List;
import lk.wedalk.common.ApiResponse;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.reviews.dto.ReviewCreateRequest;
import lk.wedalk.reviews.dto.ReviewResponse;
import lk.wedalk.reviews.service.ReviewService;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * ReviewController.java — Review REST Controller
 *
 * <p>Exposes review creation and retrieval APIs.
 *
 * <p>SCRUM-94: The POST endpoint delegates ownership validation to ReviewService,
 * which verifies the authenticated seeker is the original author of the ServiceRequest.
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    private final ReviewService reviewService;
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
     * POST /api/reviews — Create a review (seeker, after completion).
     *
     * <p>SCRUM-94: Only the seeker who originally posted the service request
     * can submit a review. Workers are blocked entirely.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @Valid @RequestBody ReviewCreateRequest request) {
        ReviewResponse response = reviewService.createReview(requireCurrentUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Review submitted successfully"));
    }

    /**
     * GET /api/reviews/worker/{workerId} — Get all reviews for a worker.
     */
    @GetMapping("/worker/{workerId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsForWorker(
            @PathVariable Long workerId) {
        List<ReviewResponse> reviews = reviewService.getReviewsForWorker(workerId);
        return ResponseEntity.ok(ApiResponse.success(reviews, "Reviews retrieved successfully"));
    }

    /**
     * GET /api/reviews/my — Get current user's submitted reviews.
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getMyReviews() {
        List<ReviewResponse> reviews = reviewService.getReviewsBySeeker(requireCurrentUserId());
        return ResponseEntity.ok(ApiResponse.success(reviews, "Your reviews retrieved successfully"));
    }
}
