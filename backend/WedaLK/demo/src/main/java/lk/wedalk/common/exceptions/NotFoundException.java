package lk.wedalk.common.exceptions;

/**
 * NotFoundException.java — Resource Not Found Exception
 *
 * This file should contain:
 * - A custom exception class extending RuntimeException
 * - Constructor accepting a message string (e.g., "User not found with id: 5")
 * - Optionally, a constructor accepting entity name + field + value for generic
 * messages
 *
 * Purpose:
 * Thrown when a requested resource (user, request, quote, etc.) does not exist.
 * Should be caught by a @ControllerAdvice global exception handler and return
 * HTTP 404.
 */
