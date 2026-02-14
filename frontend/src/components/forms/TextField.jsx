/**
 * TextField.jsx — Reusable Text Input Component (Optional Wrapper)
 *
 * This file should contain:
 * - A styled input wrapper component
 * - Props:
 *     - label: string — field label
 *     - name: string — input name attribute
 *     - type: string — 'text', 'email', 'password', 'number' (default: 'text')
 *     - value: string — current value
 *     - onChange: function — change handler
 *     - placeholder: string — placeholder text
 *     - error: string — validation error message (displayed below input)
 *     - required: boolean
 *     - disabled: boolean
 * - Structure:
 *     <div className="form-field">
 *       <label>{label}</label>
 *       <input ... />
 *       {error && <span className="field-error">{error}</span>}
 *     </div>
 *
 * Export: default TextField component
 */
