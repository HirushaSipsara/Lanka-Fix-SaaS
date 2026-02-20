package lk.wedalk.profiles.service;

/**
 * WorkerProfileService.java — Worker Profile Business Logic
 *
 * <p>This file should contain: - @Service annotation - Inject WorkerProfileRepository,
 * UserRepository - Methods: - WorkerProfileResponse createProfile(Long userId,
 * WorkerProfileCreateRequest request) - WorkerProfileResponse getProfileByUserId(Long userId) -
 * WorkerProfileResponse getProfileById(Long profileId) - WorkerProfileResponse updateProfile(Long
 * userId, WorkerProfileUpdateRequest request) - List<WorkerProfileResponse> searchWorkers(String
 * district, String skill) - List<WorkerProfileResponse> getVerifiedWorkers() - void
 * updateRating(Long profileId, double newRating)
 *
 * <p>Purpose: Manages worker profile CRUD, search, and rating updates.
 */
