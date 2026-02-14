/**
 * profileService.js — Worker Profile API Service
 *
 * This file should contain:
 * - Import apiClient from './apiClient'
 * - Functions:
 *     - createProfile(profileData) → POST /profiles
 *     - getProfileById(id) → GET /profiles/{id}
 *     - getProfileByUserId(userId) → GET /profiles/user/{userId}
 *     - updateProfile(id, profileData) → PUT /profiles/{id}
 *     - searchWorkers(district, skill) → GET /profiles/search
 *     - getVerifiedWorkers() → GET /profiles/verified
 *
 * Purpose:
 *   Centralizes all worker profile API calls.
 *   Used by worker profile pages and seeker search.
 *
 * Export: { createProfile, getProfileById, updateProfile, searchWorkers, ... }
 */
