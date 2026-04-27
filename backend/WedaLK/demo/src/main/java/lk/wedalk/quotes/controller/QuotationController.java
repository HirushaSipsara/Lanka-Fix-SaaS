package lk.wedalk.quotes.controller;

import jakarta.validation.Valid;
import lk.wedalk.common.ApiResponse;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.quotes.dto.QuoteCreateRequest;
import lk.wedalk.quotes.dto.QuoteResponse;
import lk.wedalk.quotes.service.QuotationService;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * QuotationController — REST endpoints for quotation management.
 *
 * Base path: /api/quotes
 *
 * Story 1 (this sprint):
 * POST /api/quotes — worker submits a quote
 *
 * Story 2 (this sprint):
 * GET /api/quotes/my — worker's submitted quotes
 * DELETE /api/quotes/{quoteId} — worker withdraws a quote
 *
 * Story 3 (this sprint):
 * GET /api/quotes/request/{requestId} — seeker views quotes on a request
 * PATCH /api/quotes/{quoteId}/accept — seeker accepts a quote
 * PATCH /api/quotes/{quoteId}/reject — seeker rejects a quote
 *
 * Seeker actions (accept/reject/list for request) use the authenticated user from JWT.
 */
@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class QuotationController {

    private static final Logger log = LoggerFactory.getLogger(QuotationController.class);

    private final QuotationService quotationService;
    private final UserRepository userRepository;

    private Long requireCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Authenticated user not found"))
                .getId();
    }

    // =========================================================================
    // Story 1 — Submit a Quotation
    // =========================================================================

    @PostMapping
    public ResponseEntity<ApiResponse<QuoteResponse>> submitQuote(
            @Valid @RequestBody QuoteCreateRequest request) {

        Long userId = requireCurrentUserId();
        log.info("Submitting quote: userId={}, requestId={}", userId, request.getRequestId());
        QuoteResponse response = quotationService.createQuote(userId, request);
        log.info("Quote submitted: quoteId={}", response.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Quotation submitted successfully!"));
    }

    // =========================================================================
    // Story 2 — Worker: View & Manage Quotations
    // =========================================================================

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<QuoteResponse>>> getMyQuotes() {

        List<QuoteResponse> quotes = quotationService.getQuotesByWorker(requireCurrentUserId());
        return ResponseEntity.ok(ApiResponse.success(quotes, "Quotations retrieved successfully"));
    }

    @DeleteMapping("/{quoteId}")
    public ResponseEntity<ApiResponse<QuoteResponse>> withdrawQuote(@PathVariable Long quoteId) {

        log.info("Withdrawing quote: quoteId={}", quoteId);
        QuoteResponse response = quotationService.withdrawQuote(quoteId, requireCurrentUserId());
        log.info("Quote withdrawn: quoteId={}", quoteId);
        return ResponseEntity.ok(ApiResponse.success(response, "Quotation withdrawn successfully"));
    }

    // =========================================================================
    // Story 3 — Seeker: View, Accept, Reject
    // =========================================================================

    @GetMapping("/request/{requestId}")
    public ResponseEntity<ApiResponse<List<QuoteResponse>>> getQuotesByRequest(
            @PathVariable Long requestId) {

        Long seekerId = requireCurrentUserId();
        List<QuoteResponse> quotes = quotationService.getQuotesByRequest(requestId, seekerId);
        return ResponseEntity.ok(ApiResponse.success(quotes, "Quotes retrieved successfully"));
    }

    @PatchMapping("/{quoteId}/accept")
    public ResponseEntity<ApiResponse<QuoteResponse>> acceptQuote(
            @PathVariable Long quoteId) {

        Long seekerId = requireCurrentUserId();
        log.info("Accepting quote: quoteId={}, seekerId={}", quoteId, seekerId);
        QuoteResponse response = quotationService.acceptQuote(quoteId, seekerId);
        log.info("Quote accepted: quoteId={}", quoteId);
        return ResponseEntity.ok(ApiResponse.success(response, "Quote accepted successfully"));
    }

    @PatchMapping("/{quoteId}/reject")
    public ResponseEntity<ApiResponse<QuoteResponse>> rejectQuote(
            @PathVariable Long quoteId) {

        Long seekerId = requireCurrentUserId();
        log.info("Rejecting quote: quoteId={}, seekerId={}", quoteId, seekerId);
        QuoteResponse response = quotationService.rejectQuote(quoteId, seekerId);
        return ResponseEntity.ok(ApiResponse.success(response, "Quote rejected"));
    }
}
