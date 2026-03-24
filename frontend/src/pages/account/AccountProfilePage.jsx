import React from 'react';
import { useNavigate } from 'react-router-dom';
import { getCurrentUser, logout } from '../../services/authService';
import '../auth/LoginPage.css';

const AccountProfilePage = () => {
  const navigate = useNavigate();
  const user = getCurrentUser();

  if (!user) {
    navigate('/login', { replace: true });
    return null;
  }

  const handleLogout = () => {
    logout();
    navigate('/login', { replace: true });
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-header">
          <h1>My Profile</h1>
          <p>Your account details</p>
        </div>
        <div className="login-form">
          <div className="form-group">
            <label>Full Name</label>
            <input value={user.fullName || ''} disabled />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input value={user.email || ''} disabled />
          </div>
          <div className="form-group">
            <label>Role</label>
            <input value={user.role || ''} disabled />
          </div>
          <button type="button" className="btn btn-primary btn-full" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default AccountProfilePage;
