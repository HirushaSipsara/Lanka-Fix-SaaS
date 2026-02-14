/**
 * AuthContext.jsx — Authentication Context Provider (Later)
 *
 * This file should contain:
 * - React Context for managing global auth state
 * - AuthContext creation: createContext()
 * - AuthProvider component:
 *     - State: { user, token, isAuthenticated, isLoading }
 *     - On mount: check localStorage for existing token, validate it
 *     - Methods:
 *         - login(email, password) → call authService, set state
 *         - register(formData) → call authService, set state
 *         - logout() → clear storage, reset state
 *     - Provides: { user, isAuthenticated, isLoading, login, register, logout }
 *
 * Usage:
 *   // In App.js:
 *   <AuthProvider>
 *     <AppRoutes />
 *   </AuthProvider>
 *
 *   // In any component:
 *   const { user, login, logout } = useContext(AuthContext);
 *   // Or use the useAuth() hook (preferred)
 *
 * Note: Implement after basic auth flow is working.
 *
 * Export: { AuthContext, AuthProvider }
 */
