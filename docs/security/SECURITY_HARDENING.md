# Security Hardening Baseline

This project runs in warning mode for critical configuration checks. The app will start, but it logs clear warnings if secrets or credentials look unsafe.

## Critical Environment Variables

Backend (`backend/WedaLK/demo`):

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`
- `ADMIN_EMAIL`
- `ADMIN_PASSWORD`
- `ADMIN_FULL_NAME`
- `GEMINI_API_KEY`
- `GEMINI_BASE_URL`
- `GEMINI_MODEL`
- `AI_TIMEOUT_MS`

Frontend (`frontend`):

- `REACT_APP_API_URL`

Use the provided templates:

- `.env.example`
- `backend/WedaLK/demo/.env.example`
- `frontend/.env.example`

## Warning-Mode Behavior

On backend startup, `SecurityConfigValidator` logs warnings when:

- JWT secret is missing, weak, or still default
- Admin seed password is missing or default
- DB credentials are missing or still local defaults
- Gemini API key is missing (AI Assist unavailable)

This is intentionally non-blocking for local development. For deployment, treat these warnings as release blockers.

## AI Assist Key Handling

- AI key must come from `GEMINI_API_KEY` environment variable.
- Never commit real keys into source or `application.properties`.
- If key is missing, AI Assist degrades safely and returns a user-friendly unavailable message.

## Rotation Checklist

Before production release:

1. Rotate `JWT_SECRET` to a high-entropy value.
2. Set a unique `ADMIN_PASSWORD` and rotate after initial bootstrap.
3. Rotate DB credentials away from local defaults.
4. Set `GEMINI_API_KEY` from your secret manager.
5. Confirm no secrets exist in git history.

## Local Validation Commands

Backend:

`.\mvnw.cmd -q -DskipTests compile`

Frontend:

`npm run build`

