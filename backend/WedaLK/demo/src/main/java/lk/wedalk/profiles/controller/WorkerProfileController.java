package lk.wedalk.profiles.controller;

import jakarta.validation.Valid;
import lk.wedalk.common.ApiResponse;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.profiles.dto.WorkerProfileCreateRequest;
import lk.wedalk.profiles.dto.WorkerProfileResponse;
import lk.wedalk.profiles.service.WorkerProfileService;
import lk.wedalk.users.model.User;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend
public class WorkerProfileController {

    private static final Logger log = LoggerFactory.getLogger(WorkerProfileController.class);

    private final WorkerProfileService workerProfileService;
    private final UserRepository userRepository;

    private Long requireCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Authenticated user not found"))
                .getId();
    }

    /** {@code null} when anonymous or not logged in. */
    private Long tryGetCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null
                || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return userRepository.findByEmail(auth.getName()).map(User::getId).orElse(null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkerProfileResponse>>> getAllProfiles() {
        List<WorkerProfileResponse> profiles = workerProfileService.getAllProfiles();
        return ResponseEntity.ok(ApiResponse.success(profiles, "Worker profiles retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<WorkerProfileResponse> createProfile(@Valid @RequestBody WorkerProfileCreateRequest request) {
        Long userId = requireCurrentUserId();
        log.info("Creating worker profile: userId={}", userId);
        WorkerProfileResponse response = workerProfileService.createProfile(userId, request);
        log.info("Worker profile created: profileId={}", response.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerProfileResponse> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(workerProfileService.getProfileForViewer(id, tryGetCurrentUserId()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<WorkerProfileResponse> getProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(workerProfileService.getProfileByUserIdForViewer(userId, tryGetCurrentUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerProfileResponse> updateProfile(@PathVariable Long id,
            @Valid @RequestBody lk.wedalk.profiles.dto.WorkerProfileUpdateRequest request) {
        log.info("Updating worker profile: profileId={}", id);
        return ResponseEntity.ok(workerProfileService.updateProfile(id, requireCurrentUserId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProfile(@PathVariable Long id) {
        log.info("Deleting worker profile: profileId={}", id);
        workerProfileService.deleteProfile(id, requireCurrentUserId());
        log.info("Worker profile deleted: profileId={}", id);
        return ResponseEntity.ok(ApiResponse.success(null, "Worker profile deleted successfully"));
    }
}
