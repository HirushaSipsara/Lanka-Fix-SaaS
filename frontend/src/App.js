import React, { useState, useEffect } from 'react';
import './App.css';

/**
 * App.js — Root Application Component
 *
 * Currently displays a health check to verify frontend ↔ backend connectivity.
 * In future sprints, this will:
 * - Import and render <AppRoutes /> from routes/AppRoutes
 * - Wrap with <AuthProvider> from context/AuthContext
 * - Import global styles from styles/globals.css
 */

function App() {
  const [health, setHealth] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/api/health')
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        return res.json();
      })
      .then((data) => {
        setHealth(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>🇱🇰 LankaFIX</h1>
        <p style={{ fontSize: '1.2rem', opacity: 0.8 }}>
          Find Trusted Local Workers in Sri Lanka
        </p>

        <div style={{
          marginTop: '2rem',
          padding: '1.5rem 2rem',
          borderRadius: '12px',
          backgroundColor: 'rgba(255,255,255,0.1)',
          minWidth: '300px'
        }}>
          <h3 style={{ marginTop: 0 }}>Backend Health Check</h3>

          {loading && <p>⏳ Connecting to backend...</p>}

          {error && (
            <div style={{ color: '#ff6b6b' }}>
              <p>❌ Connection failed</p>
              <p style={{ fontSize: '0.85rem' }}>Error: {error}</p>
              <p style={{ fontSize: '0.8rem', opacity: 0.7 }}>
                Make sure the Spring Boot backend is running on port 8080
              </p>
            </div>
          )}

          {health && (
            <div style={{ color: '#51cf66' }}>
              <p>✅ Status: {health.status}</p>
              <p>{health.message}</p>
              <p style={{ fontSize: '0.8rem', opacity: 0.7 }}>
                Timestamp: {health.timestamp}
              </p>
            </div>
          )}
        </div>

        <p style={{ marginTop: '2rem', fontSize: '0.85rem', opacity: 0.5 }}>
          Frontend: React on port 3000 | Backend: Spring Boot on port 8080
        </p>
      </header>
    </div>
  );
}

export default App;
