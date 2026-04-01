-- =================================================================================================
-- LankaFIX V2 — Reviews & Disputes Tables
-- Sprint 3: SCRUM-94 — Restrict review/dispute submission to involved seekers
-- =================================================================================================

-- 1. Reviews Table
CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    request_id INTEGER NOT NULL REFERENCES service_requests(id) ON DELETE CASCADE,
    reviewer_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    reviewee_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_review_per_request_reviewer UNIQUE (request_id, reviewer_id)
);

-- 2. Disputes Table
CREATE TABLE disputes (
    id SERIAL PRIMARY KEY,
    request_id INTEGER NOT NULL REFERENCES service_requests(id) ON DELETE CASCADE,
    seeker_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    worker_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    seeker_reason TEXT NOT NULL,
    worker_response TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'OPEN',
    resolution TEXT,
    resolved_by INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP
);
