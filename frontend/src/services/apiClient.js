import axios from 'axios';
import { getToken, clearAuth } from '../utils/storage';

/**
 * apiClient.js — Axios HTTP Client with JWT Authentication
 *
 * Automatically attaches JWT token from localStorage to every request
 * and handles 401 responses by clearing auth and redirecting to login.
 */
const normalizeApiBaseUrl = (url) => {
    if (!url) {
        return null;
    }

    return url
        .trim()
        .replace(/^https:\/\/https\/\//i, 'https://')
        .replace(/^http:\/\/http\/\//i, 'http://')
        .replace(/^https\/\//i, 'https://')
        .replace(/^http\/\//i, 'http://');
};

const apiClient = axios.create({
    baseURL: normalizeApiBaseUrl(process.env.REACT_APP_API_URL) || 'http://localhost:8081/api',
    timeout: 45000,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Attach JWT token to every outgoing request
apiClient.interceptors.request.use((config) => {
    const token = getToken();
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Handle 401 responses (expired/invalid token)
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            clearAuth();
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default apiClient;
