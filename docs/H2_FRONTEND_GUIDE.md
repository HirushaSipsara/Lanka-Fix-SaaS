# 🔧 Skill-Exchange Frontend Project Guide

A comprehensive overview of the project's current state, architecture, and design system.

---

## 🏗️ 1. Technical Architecture

### Core Tech Stack

- **Library**: React 18
- **Routing**: React Router DOM v6
- **Styling**: Vanilla CSS with Global Design Tokens (No Tailwind)
- **API Handling**: Axios (Custom `apiClient.js` wrapper)

### Project Structure

```text
src/
├── components/     # Reusable UI elements (Navbar, Buttons, Cards)
├── services/       # API abstraction layer (requestService, authService)
├── styles/         # Global design tokens and root variables
├── pages/          # Full page components (Landing, Portal, Wizards)
└── layouts/        # Page wrappers (MainLayout for consistent structure)
```

---

## 🎨 2. Design System: "The Ocean Theme"

The project uses a premium, high-fidelity design system focused on visual depth and clarity.

### Color Palette & Gradients

| Element                | Values                                                           |
| :--------------------- | :--------------------------------------------------------------- |
| **Primary Background** | `linear-gradient(135deg, #0a1f21 0%, #16383c 50%, #2c666d 100%)` |
| **Accent Teal**        | `#2C666E` (Primary brand color)                                  |
| **Dark Navy**          | `#1A1A2E` (Secondary backgrounds)                                |
| **Glassmorphism**      | `rgba(255, 255, 255, 0.05)` borders and overlays                 |

### Typography

- **Display Font**: `Outfit` (Headings, for a modern premium feel)
- **Body Font**: `Inter` (UI elements and long text, for clarity)
- **Material Icons**: Standardized icons for intuitive navigation.

---

## 🚀 3. Implemented Features & Pages

### 🏠 Landing Page (`/`)

- Public-facing page showcasing the service vision.
- uses the `landing` variant of the shared Navbar.

### 📋 My Requests Page (`/my-requests`)

- **Seeker Portal**: A centralized list of all service requests.
- **Features**: Filtering, sorting, and status-colored badges.
- **UI**: High-contrast "Ocean Theme" cards with hover effects.

### ✍️ Post Request Wizard (`/create-request`)

- **Multi-Step Flow**:
  1.  **Category & Location**: Visual selection with emojis.
  2.  **Details & Urgency**: Description input and urgency tier selection.
  3.  **Review**: Final validation before posting.
- **Validation**: Robust error checking at each step.

### 📄 Request Details Page (`/my-requests/:id`)

- **Real-Time Data**: Fetches data dynamically from the API by ID.
- **Timeline**: 4-stage tracking flow (Posted → Quotes → Hire → Completion).
- **Back Navigation**: Glassmorphic pill-shaped back button with smooth transitions.

---

## 🛠️ 4. Shared Components & Patterns

### 1. Reusable Navbar (`Navbar.jsx`)

- Supports **Landing** (transparent/blur) and **Portal** (solid/consistent) modes.
- Integrated mobile drawer for responsive navigation.
- Consistent branding: **LankaFIX 🔧**.

### 2. Service Layer Pattern

- Controllers call functions from `requestService.js` rather than using `fetch` directly.
- Handles data transformation (e.g., parsing ISO dates to readable formats).

---

## 📈 5. Maintenance & Next Steps (Developer Notes)

1.  **Iconography**: Use `<span className="material-icons">icon_name</span>` for all vector icons.
2.  **Styles**: Avoid inline styles. Use CSS variables defined in `globals.css` (e.g., `var(--primary)`).
3.  **State**: When adding new portal pages, wrap them in the `page-wrapper` class to inherit the global ocean gradient.

---

_Last Updated: Feb 17, 2026_
