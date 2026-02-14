/**
 * SelectField.jsx — Reusable Select Dropdown Component
 *
 * This file should contain:
 * - A styled select/dropdown wrapper component
 * - Props:
 *     - label: string — field label
 *     - name: string — select name attribute
 *     - value: string — current selected value
 *     - onChange: function — change handler
 *     - options: array of { value, label } — dropdown options
 *     - placeholder: string — default unselected text (e.g., "Select a district...")
 *     - error: string — validation error message
 *     - required: boolean
 *     - disabled: boolean
 * - Structure:
 *     <div className="form-field">
 *       <label>{label}</label>
 *       <select ...>
 *         <option disabled>{placeholder}</option>
 *         {options.map(opt => <option key={opt.value} value={opt.value}>{opt.label}</option>)}
 *       </select>
 *       {error && <span className="field-error">{error}</span>}
 *     </div>
 *
 * Usage:
 *   <SelectField label="District" options={DISTRICTS} ... />
 *
 * Export: default SelectField component
 */
