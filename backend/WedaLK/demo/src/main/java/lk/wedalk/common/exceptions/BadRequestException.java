package lk.wedalk.common.exceptions;

/**
 * BadRequestException.java — Bad Request Exception
 *
 * This file should contain:
 * - A custom exception class extending RuntimeException
 * - Constructor accepting a message string
 *
 * Purpose:
 * Thrown when the client sends invalid data (e.g., missing required fields,
 * invalid status transitions, duplicate submissions).
 * Should be caught by a @ControllerAdvice global exception handler and return
 * HTTP 400.
 */
