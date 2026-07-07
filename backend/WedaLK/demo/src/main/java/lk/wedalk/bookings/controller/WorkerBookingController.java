package lk.wedalk.bookings.controller;

import jakarta.validation.Valid;
import java.util.List;
import lk.wedalk.bookings.dto.BookingCreateRequest;
import lk.wedalk.bookings.dto.BookingResponse;
import lk.wedalk.bookings.service.WorkerBookingService;
import lk.wedalk.common.ApiResponse;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Direct worker bookings (date + time range). Seeker books; worker accepts or rejects.
 * Base path: /api/bookings
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class WorkerBookingController {

  private final WorkerBookingService bookingService;
  private final UserRepository userRepository;

  private Long requireCurrentUserId() {
    return userRepository
        .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
        .orElseThrow(() -> new NotFoundException("Authenticated user not found"))
        .getId();
  }

  @PostMapping
  public ResponseEntity<ApiResponse<BookingResponse>> create(@Valid @RequestBody BookingCreateRequest request) {
    BookingResponse res = bookingService.createBooking(requireCurrentUserId(), request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(res, "Booking request sent. The worker will confirm based on their schedule."));
  }

  @GetMapping("/seeker")
  public ResponseEntity<ApiResponse<List<BookingResponse>>> listForSeeker() {
    List<BookingResponse> list = bookingService.listForSeeker(requireCurrentUserId());
    return ResponseEntity.ok(ApiResponse.success(list, "OK"));
  }

  @GetMapping("/worker")
  public ResponseEntity<ApiResponse<List<BookingResponse>>> listForWorker() {
    List<BookingResponse> list = bookingService.listForWorker(requireCurrentUserId());
    return ResponseEntity.ok(ApiResponse.success(list, "OK"));
  }

  @PatchMapping("/{id}/accept")
  public ResponseEntity<ApiResponse<BookingResponse>> accept(@PathVariable Long id) {
    BookingResponse res = bookingService.acceptBooking(requireCurrentUserId(), id);
    return ResponseEntity.ok(ApiResponse.success(res, "Booking accepted."));
  }

  @PatchMapping("/{id}/reject")
  public ResponseEntity<ApiResponse<BookingResponse>> reject(@PathVariable Long id) {
    BookingResponse res = bookingService.rejectBooking(requireCurrentUserId(), id);
    return ResponseEntity.ok(ApiResponse.success(res, "Booking rejected."));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable Long id) {
    bookingService.cancelBySeeker(requireCurrentUserId(), id);
    return ResponseEntity.ok(ApiResponse.success(null, "Booking cancelled."));
  }
}
