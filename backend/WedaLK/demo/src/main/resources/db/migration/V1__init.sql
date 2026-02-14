-- =================================================================================================
-- LankaFIX V1 Database Schema
-- Based on Sprint 1 Epics: Users, Worker Profiles, Service Requests
-- Run this script in your local PostgreSQL database (e.g., via pgAdmin) to create the tables.
-- =================================================================================================

-- 1. Users Table (Core Auth)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Hashed password
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,      -- 'SEEKER', 'WORKER', 'ADMIN'
    phone_number VARCHAR(20),
    district VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Worker Profiles (Epic 1)
-- Linked to Users table. One User (WORKER) has One Profile.
CREATE TABLE worker_profiles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    bio TEXT,
    skills TEXT,                    -- Comma-separated or JSON array of skills
    service_areas TEXT,             -- Comma-separated districts
    hourly_rate DECIMAL(10, 2),
    availability_status VARCHAR(50) DEFAULT 'AVAILABLE', -- 'AVAILABLE', 'BUSY'
    verification_status VARCHAR(50) DEFAULT 'NONE',      -- 'NONE', 'PENDING', 'APPROVED'
    rating DECIMAL(3, 2) DEFAULT 0.0,
    total_jobs_completed INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Service Requests (Epic 2)
-- Linked to Users (Seeker).
CREATE TABLE service_requests (
    id SERIAL PRIMARY KEY,
    seeker_id INTEGER NOT NULL REFERENCES users(id),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(100) NOT NULL, -- 'Plumbing', 'Electrical', etc.
    location_area VARCHAR(100) NOT NULL,
    address TEXT,
    budget_min DECIMAL(10, 2),
    budget_max DECIMAL(10, 2),
    preferred_date DATE,
    status VARCHAR(50) DEFAULT 'OPEN', -- 'OPEN', 'ASSIGNED', 'COMPLETED', 'CANCELLED'
    assigned_worker_id INTEGER REFERENCES users(id), -- Nullable until assigned
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Initial Data (Optional for Testing)
INSERT INTO users (email, password, full_name, role, district) VALUES 
('seeker@wd.lk', 'password123', 'John Seeker', 'SEEKER', 'Colombo'),
('worker@wd.lk', 'password123', 'Jane Worker', 'WORKER', 'Gampaha');

INSERT INTO worker_profiles (user_id, bio, skills, service_areas, hourly_rate) VALUES
(2, 'Experienced electrician with 5 years of field work.', 'Electrical,Wiring,Repair', 'Colombo,Gampaha', 1500.00);

INSERT INTO service_requests (seeker_id, title, description, category, location_area, budget_min, budget_max) VALUES
(1, 'Fix leaking tap', 'Kitchen tap is leaking heavily.', 'Plumbing', 'Colombo 03', 2000.00, 5000.00);
