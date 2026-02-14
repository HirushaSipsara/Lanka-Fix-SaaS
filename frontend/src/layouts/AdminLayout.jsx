/**
 * AdminLayout.jsx — Admin Dashboard Layout (Later)
 *
 * This file should contain:
 * - A layout wrapper specific to admin pages
 * - Structure:
 *     <div className="admin-layout">
 *       <aside className="admin-sidebar">
 *         ← Admin navigation sidebar with links to:
 *            - Dashboard
 *            - Verification Review
 *            - Dispute Review
 *            - User Management
 *       </aside>
 *       <div className="admin-content">
 *         <header className="admin-header">
 *           ← Admin top bar with user info and logout
 *         </header>
 *         <main>
 *           <Outlet />
 *         </main>
 *       </div>
 *     </div>
 * - Import { Outlet, NavLink } from 'react-router-dom'
 *
 * Purpose:
 *   Provides a sidebar navigation layout for admin panel pages.
 *
 * Export: default AdminLayout component
 */
