import axios from 'axios';
import { getToken, clearAuth } from '../utils/storage';

const apiBaseUrl = process.env.REACT_APP_API_URL || 'http://localhost:8081/api';

if (!process.env.REACT_APP_API_URL && process.env.NODE_ENV !== 'production') {
  // Non-blocking warning so local/dev sessions notice missing env wiring early.
  // eslint-disable-next-line no-console
  console.warn('REACT_APP_API_URL is not set. Falling back to http://localhost:8081/api');
}

const apiClient = axios.create({
  baseURL: apiBaseUrl,
  // No default Content-Type: axios auto-sets application/json for plain objects
  // and lets the browser attach the correct multipart/form-data; boundary=... for FormData.
});

apiClient.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error?.response?.status === 401) {
      clearAuth();
    }
    return Promise.reject(error);
  }
);

export default apiClient;
