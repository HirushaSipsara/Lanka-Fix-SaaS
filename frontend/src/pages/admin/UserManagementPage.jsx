/**
 * UserManagementPage.jsx — Admin User Management Page
 *
 * This file should contain:
 * - A searchable, filterable list of all platform users
 * - Table/list with columns:
 *     - Name, Email, Role, District, Status (Active/Suspended), Joined Date
 * - Filters:
 *     - Role: All, Seeker, Worker
 *     - Status: All, Active, Suspended
 * - Search by name or email
 * - Actions per user:
 *     - View profile (link)
 *     - Suspend user (with reason prompt)
 *     - Unsuspend user
 * - Pagination for large user lists
 * - Fetch: adminService.getAllUsers(filters)
 *
 * Export: default UserManagementPage component
 */
