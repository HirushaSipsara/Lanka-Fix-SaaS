/**
 * Navbar.jsx — Top Navigation Bar
 *
 * This file should contain:
 * - A responsive navigation bar component
 * - Elements:
 *     - Logo/brand name (LankaFIX) — links to home
 *     - Navigation links (based on auth state):
 *         - Not logged in: Home, Login, Register
 *         - Seeker: Dashboard, My Requests, Create Request
 *         - Worker: Dashboard, Browse Requests, My Profile
 *         - Admin: Admin Dashboard
 *     - User info display (name, avatar) when logged in
 *     - Logout button when logged in
 *     - Mobile hamburger menu for responsive design
 * - Import { Link, useNavigate } from 'react-router-dom'
 * - Import useAuth hook for auth state (later)
 *
 * Purpose:
 *   Persistent top navigation across all non-admin pages.
 *
 * Export: default Navbar component
 */
