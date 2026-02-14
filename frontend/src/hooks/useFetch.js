/**
 * useFetch.js — Generic Data Fetching Hook (Optional)
 *
 * This file should contain:
 * - A custom React hook for fetching data from API
 * - Parameters:
 *     - url: string        — API endpoint
 *     - options: object     — { method, body, headers } (optional)
 *     - dependencies: array — re-fetch when these change (like useEffect deps)
 * - Returns:
 *     - data: any          — response data (null initially)
 *     - loading: boolean   — whether request is in flight
 *     - error: string      — error message if request failed
 *     - refetch: function  — manually trigger a re-fetch
 *
 * Usage:
 *   const { data: requests, loading, error } = useFetch('/requests/open');
 *   const { data, refetch } = useFetch(`/requests/${id}`, {}, [id]);
 *
 * Implementation:
 *   - Uses useEffect to fetch on mount and when dependencies change
 *   - Uses apiClient for requests (includes auth headers)
 *   - Handles cleanup with AbortController to prevent memory leaks
 *
 * Export: default useFetch hook
 */
