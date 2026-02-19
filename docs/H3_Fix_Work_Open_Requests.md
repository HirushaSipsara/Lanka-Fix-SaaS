# Plan: Improve "Worker Find Work" Feature (SCRUM-14)

**Task:** Worker wants to see a list of open requests so that I can decide which jobs to quote for.

## What's Currently Working

- Backend `GET /api/requests/open` returns all open requests (sorted by createdAt DESC)
- Backend `GET /api/requests/search?locationArea=X&category=Y` supports filtering
- Frontend `FindWorkPage.jsx` displays cards with category, urgency, location, description, budget, date
- Frontend `requestService.js` has `searchRequests(filters)` built but **unused**
- `RequestDetailsPage.jsx` shows context-aware view (worker vs seeker)

## What's Missing / Needs Improvement

### 1. No Filter/Search UI on FindWorkPage

The backend search endpoint exists, the frontend service function exists, but there's no UI to use them. Workers can't narrow down requests by category or location.

### 2. No Sorting

Workers can't sort by budget, urgency, or date. Only default order (newest) from backend.

### 3. No Results Count

Page doesn't show how many jobs are available (e.g., "12 jobs available").

### 4. Budget Display — No Currency Formatting

Budget shows raw number. Should display as "Rs. 5,000". Also `req.budget || 'Negotiable'` incorrectly treats `0` as falsy.

### 5. Fragile Worker Context Detection

`isWorker = location.state?.from === 'find-work'` is lost on page refresh. Should use URL path instead — `/requests/:id` = worker, `/my-requests/:id` = seeker (routes already defined this way in App.js).

### 6. "Send Quote" Button Does Nothing

Button on RequestDetailsPage has no onClick handler. Will add a simple alert ("coming soon").

---

## Implementation Plan

### Step 1: Populate `constants.js` with shared utilities

**File:** `frontend/src/utils/constants.js`

- Export `CATEGORIES` array (value, label, icon for all 12 categories)
- Export `URGENCY_LEVELS` array (value, label, weight for sorting)
- Export `getCategoryIcon(category)` helper
- Export `formatCategoryLabel(category)` helper
- Export `formatBudget(budget)` helper — returns `"Rs. X,XXX"`, `"Negotiable"` for null/undefined, `"Free / Volunteer"` for 0

### Step 2: Fix budget formatting in FindWorkPage

**File:** `frontend/src/pages/worker/FindWorkPage.jsx`

- Import `formatBudget`, `getCategoryIcon`, `CATEGORIES` from constants
- Remove inline `getCategoryIcon` function (use shared one)
- Remove `budget: req.budget || 'Negotiable'` transform (keep raw value for sorting)
- Use `formatBudget(req.budget)` in render

### Step 3: Add filter bar + search to FindWorkPage

**Files:** `FindWorkPage.jsx`, `FindWorkPage.css`

- Add state: `selectedCategory`, `locationSearch`, `activeFilters`, `allRequests`
- Import `searchRequests` from requestService
- Modify `fetchRequests` to accept optional filters — call `searchRequests()` when filters active, `getOpenRequests()` when not
- Add filter bar JSX after header: location text input, category `<select>` dropdown, Search button, Clear button
- Add responsive CSS for the filter bar (glass-morphism style matching page theme)

### Step 4: Add sorting (client-side)

**Files:** `FindWorkPage.jsx`, `FindWorkPage.css`

- Add `sortBy` state (default: `'newest'`)
- Add sort `<select>` dropdown: Newest First, Budget High→Low, Budget Low→High, Most Urgent
- Add `useEffect` watching `[sortBy, allRequests]` that sorts and sets `requests`
- Uses `URGENCY_LEVELS` weight values for urgency sorting

### Step 5: Add results count

**Files:** `FindWorkPage.jsx`, `FindWorkPage.css`

- Show `"{count} jobs available"` or `"{count} jobs found"` (when filtered) above the card grid
- Small CSS addition for styling

### Step 6: Fix worker context in RequestDetailsPage

**File:** `frontend/src/pages/seeker/RequestDetailsPage.jsx`

- Change `const isWorker = location.state?.from === 'find-work'` → `const isWorker = !location.pathname.startsWith('/my-requests')`
- This survives page refresh since it uses URL path, not ephemeral state
- Fix error page back link to also be context-aware

### Step 7: Fix budget formatting in RequestDetailsPage

**File:** `frontend/src/pages/seeker/RequestDetailsPage.jsx`

- Import `formatBudget` from constants
- Remove `budget: data.budget || 'Not specified'` transform
- Use `formatBudget(request.budget)` in render

### Step 8: Add Send Quote placeholder

**File:** `frontend/src/pages/seeker/RequestDetailsPage.jsx`

- Add `onClick` handler to Send Quote button: `alert('Quote submission is coming soon!')`

---

## Files Changed Summary

| File                                               | What Changes                                                                                 |
| -------------------------------------------------- | -------------------------------------------------------------------------------------------- |
| `frontend/src/utils/constants.js`                  | Populate with CATEGORIES, URGENCY_LEVELS, getCategoryIcon, formatCategoryLabel, formatBudget |
| `frontend/src/pages/worker/FindWorkPage.jsx`       | Add filter bar, sorting, results count, use shared constants, budget formatting              |
| `frontend/src/pages/worker/FindWorkPage.css`       | Styles for filter bar, sort dropdown, results count                                          |
| `frontend/src/pages/seeker/RequestDetailsPage.jsx` | Fix isWorker check, budget formatting, Send Quote alert                                      |

**No backend changes required.** All needed API endpoints already exist.

## Verification

1. Start the backend (`mvn spring-boot:run` on port 8081)
2. Start the frontend (`npm start` on port 3000)
3. Navigate to `/find-work` — verify:
   - Filter bar is visible with location input, category dropdown, sort dropdown
   - Selecting a category and clicking Search filters the results
   - Typing a location and pressing Enter/Search filters results
   - Clear button resets filters
   - Sort dropdown reorders cards correctly
   - Results count shows correct number
   - Budget displays as "Rs. X,XXX" format
4. Click "View Details" on a card — verify:
   - Page shows worker context (Send Quote button, no Edit/Delete)
   - Refresh the page — worker context persists (not lost)
   - Click "Send Quote" — shows "coming soon" alert
   - Budget shows formatted with "Rs." prefix
   - "Back to Find Work" link works
