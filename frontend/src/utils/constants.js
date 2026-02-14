/**
 * constants.js — Application Constants
 *
 * This file should contain:
 * - Enum-like objects matching backend enums:
 *
 *   export const ROLES = { SEEKER: 'SEEKER', WORKER: 'WORKER', ADMIN: 'ADMIN' };
 *
 *   export const REQUEST_STATUS = {
 *     OPEN: 'OPEN', ASSIGNED: 'ASSIGNED', COMPLETED: 'COMPLETED',
 *     NOT_COMPLETED: 'NOT_COMPLETED', CANCELLED: 'CANCELLED'
 *   };
 *
 *   export const QUOTE_STATUS = { PENDING: 'PENDING', ACCEPTED: 'ACCEPTED', REJECTED: 'REJECTED' };
 *
 *   export const VERIFICATION_STATUS = {
 *     NONE: 'NONE', PENDING: 'PENDING', APPROVED: 'APPROVED', REJECTED: 'REJECTED'
 *   };
 *
 *   export const DISPUTE_STATUS = { OPEN: 'OPEN', RESOLVED: 'RESOLVED' };
 *
 * - Sri Lankan districts list:
 *   export const DISTRICTS = [
 *     'Colombo', 'Gampaha', 'Kalutara', 'Kandy', 'Matale', 'Nuwara Eliya',
 *     'Galle', 'Matara', 'Hambantota', 'Jaffna', 'Kilinochchi', 'Mannar',
 *     'Mullaitivu', 'Vavuniya', 'Trincomalee', 'Batticaloa', 'Ampara',
 *     'Kurunegala', 'Puttalam', 'Anuradhapura', 'Polonnaruwa', 'Badulla',
 *     'Monaragala', 'Ratnapura', 'Kegalle'
 *   ];
 *
 * - Service categories:
 *   export const CATEGORIES = [
 *     'Plumbing', 'Electrical', 'Painting', 'Carpentry', 'Cleaning',
 *     'Gardening', 'AC Repair', 'Appliance Repair', 'Moving', 'Other'
 *   ];
 *
 * - API base URL:
 *   export const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
 *
 * Purpose:
 *   Single source of truth for all constants used across the frontend.
 */
