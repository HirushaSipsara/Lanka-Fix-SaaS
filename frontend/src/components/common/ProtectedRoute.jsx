/**
 * ProtectedRoute.jsx — Route Guard Component (Later)
 *
 * This file should contain:
 * - A wrapper component that checks authentication and role
 * - Props:
 *     - allowedRoles: array of roles that can access (e.g., ['SEEKER', 'WORKER'])
 *     - children or <Outlet />
 * - Logic:
 *     - If not authenticated → redirect to /login
 *     - If authenticated but wrong role → redirect to /unauthorized or home
 *     - If authenticated and correct role → render children/outlet
 * - Import useAuth hook for auth state
 * - Import { Navigate, Outlet } from 'react-router-dom'
 *
 * Usage:
 *   <Route element={<ProtectedRoute allowedRoles={['SEEKER']} />}>
 *     <Route path="/seeker/dashboard" element={<SeekerDashboard />} />
 *   </Route>
 *
 * Export: default ProtectedRoute component
 */
