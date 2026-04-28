/**
 * validators.js — Reusable form validation helpers.
 *
 * Note: Keep these utilities UI-agnostic. They return a user-facing error string
 * or `undefined` when the value is valid.
 */

const asTrimmedString = (value) => String(value ?? '').trim();

export function isRequired(value, message = 'This field is required.') {
  return asTrimmedString(value) ? undefined : message;
}

export function isValidEmail(value, message = 'Use a valid email address.') {
  const t = asTrimmedString(value);
  if (!t) return message;
  // Loose, user-facing email check. Backend enforces canonical validation too.
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(t) ? undefined : message;
}

export function isMinLength(value, min, message = `Must be at least ${min} characters.`) {
  const t = asTrimmedString(value);
  if (!t) return undefined;
  return t.length >= min ? undefined : message;
}

export function isMaxLength(value, max, message = `Must be ${max} characters or fewer.`) {
  const t = asTrimmedString(value);
  if (!t) return undefined;
  return t.length <= max ? undefined : message;
}

export function isPositiveNumber(value, message = 'Must be greater than zero.') {
  if (value === '' || value === null || typeof value === 'undefined') return undefined;
  const n = typeof value === 'number' ? value : Number(String(value).trim());
  if (!Number.isFinite(n)) return 'Must be a valid number.';
  return n > 0 ? undefined : message;
}

export function isNonNegativeNumber(value, message = 'Must be zero or a positive number.') {
  if (value === '' || value === null || typeof value === 'undefined') return undefined;
  const n = typeof value === 'number' ? value : Number(String(value).trim());
  if (!Number.isFinite(n)) return 'Must be a valid number.';
  return n >= 0 ? undefined : message;
}

/**
 * Sri Lankan mobile number (user-facing, tolerant):
 * - 07XXXXXXXX
 * - +947XXXXXXXX / 947XXXXXXXX
 * - allows spaces and dashes
 */
export function isValidPhone(value, message = 'Use a valid phone number (e.g., 0771234567).') {
  const t = asTrimmedString(value);
  if (!t) return undefined;
  const normalized = t.replace(/[\s-]/g, '');
  return /^(?:\+94|94|0)7\d{8}$/.test(normalized) ? undefined : message;
}

export function passwordsMatch(password, confirmPassword, message = 'Passwords must match.') {
  if (!password && !confirmPassword) return undefined;
  return password === confirmPassword ? undefined : message;
}

export function isWithinBudgetRange(min, max, message = 'Minimum budget must be less than or equal to maximum budget.') {
  if (min === '' || min === null || typeof min === 'undefined') return undefined;
  if (max === '' || max === null || typeof max === 'undefined') return undefined;
  const minN = Number(min);
  const maxN = Number(max);
  if (!Number.isFinite(minN) || !Number.isFinite(maxN)) return 'Budget values must be valid numbers.';
  return minN <= maxN ? undefined : message;
}

/**
 * Runs rules per field and returns a flat errors object:
 * { [fieldName]: 'Error message' }
 *
 * `rules` is:
 * { fieldName: [(value, formData) => string|undefined, ...] }
 */
export function validateForm(formData, rules) {
  const errors = {};
  Object.entries(rules || {}).forEach(([field, validators]) => {
    const fns = Array.isArray(validators) ? validators : [];
    for (const fn of fns) {
      const msg = fn?.(formData?.[field], formData);
      if (typeof msg === 'string' && msg.trim()) {
        errors[field] = msg.trim();
        break;
      }
    }
  });
  return errors;
}
