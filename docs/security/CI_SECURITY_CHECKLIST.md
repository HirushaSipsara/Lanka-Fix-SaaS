# CI Security Checklist

Use this checklist as a minimum gate before merge/release.

## Secret Hygiene

- [ ] `.env`, credentials, and API keys are never committed.
- [ ] `.env.example` files contain placeholders only.
- [ ] Pull requests are scanned for leaked secrets.

## Dependency Risk

- [ ] Frontend dependency audit runs (`npm audit --audit-level=high`).
- [ ] Backend dependencies are reviewed and updated regularly.
- [ ] Critical vulnerabilities are patched or explicitly risk-accepted.

## Build/Integrity

- [ ] Backend compile/test passes.
- [ ] Frontend build/lint passes.
- [ ] Security warnings from startup validator are reviewed.

## Deployment Readiness

- [ ] `JWT_SECRET` is set from secret manager.
- [ ] `ADMIN_PASSWORD` is not default.
- [ ] DB credentials are not local defaults.
- [ ] `GEMINI_API_KEY` is configured for AI Assist.

