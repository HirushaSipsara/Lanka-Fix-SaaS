import axios from 'axios';
import { getToken, clearAuth } from '../utils/storage';

const apiClient = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8081/api',
  // No default Content-Type: axios auto-sets application/json for plain objects
  // and lets the browser attach the correct multipart/form-data; boundary=... for FormData.
});

apiClient.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  // #region agent log
  try {
    fetch('http://127.0.0.1:7485/ingest/9a59d784-0ca8-4645-87dc-cb3f3ea01e9c', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', 'X-Debug-Session-Id': 'ba51df' },
      body: JSON.stringify({
        sessionId: 'ba51df',
        runId: 'run1',
        hypothesisId: 'H1,H5',
        location: 'apiClient.js:request-interceptor',
        message: 'outgoing request',
        data: {
          method: (config.method || 'get').toUpperCase(),
          url: config.url,
          hasToken: !!token,
          tokenLength: token ? token.length : 0,
          tokenPrefix: token ? token.slice(0, 12) : null,
          tokenSuffix: token ? token.slice(-6) : null,
          contentType: config.headers['Content-Type'] || config.headers['content-type'] || null,
        },
        timestamp: Date.now(),
      }),
    }).catch(() => {});
  } catch (e) {}
  // #endregion
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    // #region agent log
    try {
      fetch('http://127.0.0.1:7485/ingest/9a59d784-0ca8-4645-87dc-cb3f3ea01e9c', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'X-Debug-Session-Id': 'ba51df' },
        body: JSON.stringify({
          sessionId: 'ba51df',
          runId: 'run1',
          hypothesisId: 'H1,H5',
          location: 'apiClient.js:response-interceptor',
          message: 'response error',
          data: {
            status: error?.response?.status,
            url: error?.config?.url,
            method: (error?.config?.method || '').toUpperCase(),
            willClearAuth: error?.response?.status === 401,
            backendMessage: error?.response?.data?.message || error?.response?.data?.error || null,
          },
          timestamp: Date.now(),
        }),
      }).catch(() => {});
    } catch (e) {}
    // #endregion
    if (error?.response?.status === 401) {
      clearAuth();
    }
    return Promise.reject(error);
  }
);

export default apiClient;
