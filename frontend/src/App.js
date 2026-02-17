import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';

import MainLayout from './layouts/MainLayout';
import LandingPage from './pages/public/LandingPage';

import WorkerProfileForm from './components/WorkerProfileForm';

import WorkerProfileView from './components/WorkerProfileView';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/" element={<LandingPage />} />
          <Route path="/create-profile" element={<WorkerProfileForm />} />
          <Route path="/edit-profile/:id" element={<WorkerProfileForm />} />
          <Route path="/profile/:id" element={<WorkerProfileView />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
