/**
 * MainLayout.jsx — Main Application Layout
 *
 * This file should contain:
 * - A layout wrapper component used by most pages
 * - Structure:
 *     <div className="main-layout">
 *       <Navbar />            ← persistent top navigation
 *       <main className="content">
 *         <Outlet />          ← React Router renders child routes here
 *       </main>
 *       <Footer />            ← persistent footer
 *     </div>
 * - Import Navbar and Footer from components/common
 * - Import { Outlet } from 'react-router-dom'
 *
 * Purpose:
 *   Wraps all public and authenticated pages with consistent
 *   navigation and footer. Admin pages use AdminLayout instead.
 *
 * Export: default MainLayout component
 */
