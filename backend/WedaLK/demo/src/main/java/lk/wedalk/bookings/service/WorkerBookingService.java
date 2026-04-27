package lk.wedalk.bookings.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lk.wedalk.bookings.dto.BookingCreateRequest;
import lk.wedalk.bookings.dto.BookingResponse;
import lk.wedalk.bookings.model.WorkerBooking;
import lk.wedalk.bookings.repository.WorkerBookingRepository;
import lk.wedalk.common.enums.BookingStatus;
import lk.wedalk.common.exceptions.BadRequestException;
import lk.wedalk.common.exceptions.NotFoundException;
import lk.wedalk.profiles.model.WorkerProfile;
import lk.wedalk.profiles.repository.WorkerProfileRepository;
import lk.wedalk.users.model.Role;
import lk.wedalk.users.model.User;
import lk.wedalk.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkerBookingService {

  private final WorkerBookingRepository bookingRepository;
  private final WorkerProfileRepository workerProfileRepository;
  private final UserRepository userRepository;

  @Transactional
  public BookingResponse createBooking(Long seekerId, BookingCreateRequest req) {
    if (!req.getEndTime().isAfter(req.getStartTime())) {
      throw new BadRequestException("End time must be after start time.");
    }
    if (req.getBookingDate().isBefore(LocalDate.now())) {
      throw new BadRequestException("Booking date cannot be in the past.");
    }
    if (req.getStartTime().equals(req.getEndTime())) {
      throw new BadRequestException("Start and end time cannot be the same.");
    }

    User seeker = userRepository
        .findById(seekerId)
        .orElseThrow(() -> new NotFoundException("User not found"));
    if (seeker.getRole() != Role.SEEKER) {
      throw new BadRequestException("Only seekers can create bookings.");
    }

    WorkerProfile profile = workerProfileRepository
        .findById(req.getWorkerProfileId())
        .orElseThrow(() -> new NotFoundException("Worker profile not found"));

    User worker = profile.getUser();
    if (worker.getRole() != Role.WORKER) {
      throw new BadRequestException("This profile is not a worker profile.");
    }
    if (worker.getId().equals(seekerId)) {
      throw new BadRequestException("You cannot book yourself.");
    }

    if (hasOverlapWithExistingForWorker(
        worker.getId(), req.getBookingDate(), req.getStartTime(), req.getEndTime(), null)) {
      throw new BadRequestException(
          "This worker already has a pending or accepted booking in that time range.");
    }

    WorkerBooking booking = WorkerBooking.builder()
        .seeker(seeker)
        .worker(worker)
        .workerProfileId(profile.getId())
        .bookingDate(req.getBookingDate())
        .startTime(req.getStartTime())
        .endTime(req.getEndTime())
        .note(trimNote(req.getNote()))
        .status(BookingStatus.PENDING)
        .build();

    booking = bookingRepository.save(booking);
    return toSeekerResponse(booking);
  }

  @Transactional(readOnly = true)
  public List<BookingResponse> listForSeeker(Long seekerId) {
    return bookingRepository.findBySeeker_IdOrderByBookingDateDescStartTimeDesc(seekerId).stream()
        .map(this::toSeekerResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<BookingResponse> listForWorker(Long workerUserId) {
    return bookingRepository.findByWorker_IdOrderByBookingDateDescStartTimeDesc(workerUserId).stream()
        .map(this::toWorkerResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public BookingResponse acceptBooking(Long workerUserId, Long bookingId) {
    WorkerBooking booking = getBookingOrThrow(bookingId);
    if (!booking.getWorker().getId().equals(workerUserId)) {
      throw new BadRequestException("You can only update your own bookings.");
    }
    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("Only pending bookings can be accepted.");
    }
    if (hasOverlapWithExistingForWorker(
        booking.getWorker().getId(),
        booking.getBookingDate(),
        booking.getStartTime(),
        booking.getEndTime(),
        booking.getId())) {
      throw new BadRequestException(
          "This slot overlaps with another accepted or pending booking on your schedule.");
    }
    booking.setStatus(BookingStatus.ACCEPTED);
    return toWorkerResponse(bookingRepository.save(booking));
  }

  @Transactional
  public BookingResponse rejectBooking(Long workerUserId, Long bookingId) {
    WorkerBooking booking = getBookingOrThrow(bookingId);
    if (!booking.getWorker().getId().equals(workerUserId)) {
      throw new BadRequestException("You can only update your own bookings.");
    }
    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("Only pending bookings can be rejected.");
    }
    booking.setStatus(BookingStatus.REJECTED);
    return toWorkerResponse(bookingRepository.save(booking));
  }

  @Transactional
  public void cancelBySeeker(Long seekerId, Long bookingId) {
    WorkerBooking booking = getBookingOrThrow(bookingId);
    if (!booking.getSeeker().getId().equals(seekerId)) {
      throw new BadRequestException("You can only cancel your own bookings.");
    }
    if (booking.getStatus() != BookingStatus.PENDING) {
      throw new BadRequestException("You can only cancel a booking that is still pending.");
    }
    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepository.save(booking);
  }

  private WorkerBooking getBookingOrThrow(Long bookingId) {
    return bookingRepository
        .findById(bookingId)
        .orElseThrow(() -> new NotFoundException("Booking not found"));
  }

  private boolean hasOverlapWithExistingForWorker(
      Long workerId,
      LocalDate date,
      LocalTime start,
      LocalTime end,
      Long excludeBookingId) {
    List<WorkerBooking> sameDay = bookingRepository.findByWorker_IdAndBookingDateAndStatusIn(
        workerId, date, List.of(BookingStatus.PENDING, BookingStatus.ACCEPTED));
    for (WorkerBooking b : sameDay) {
      if (excludeBookingId != null && b.getId().equals(excludeBookingId)) {
        continue;
      }
      if (overlaps(start, end, b.getStartTime(), b.getEndTime())) {
        return true;
      }
    }
    return false;
  }

  private static boolean overlaps(
      LocalTime aStart, LocalTime aEnd, LocalTime bStart, LocalTime bEnd) {
    return aStart.isBefore(bEnd) && aEnd.isAfter(bStart);
  }

  private static String trimNote(String note) {
    if (note == null) {
      return null;
    }
    String t = note.trim();
    return t.isEmpty() ? null : t;
  }

  private BookingResponse toSeekerResponse(WorkerBooking b) {
    return BookingResponse.builder()
        .id(b.getId())
        .status(b.getStatus())
        .bookingDate(b.getBookingDate())
        .startTime(b.getStartTime())
        .endTime(b.getEndTime())
        .note(b.getNote())
        .workerProfileId(b.getWorkerProfileId())
        .workerName(b.getWorker().getFullName())
        .workerPhone(
            b.getStatus() == BookingStatus.ACCEPTED ? b.getWorker().getPhoneNumber() : null)
        .seekerName(b.getSeeker().getFullName())
        .seekerPhone(b.getSeeker().getPhoneNumber())
        .createdAt(b.getCreatedAt())
        .updatedAt(b.getUpdatedAt())
        .build();
  }

  private BookingResponse toWorkerResponse(WorkerBooking b) {
    return BookingResponse.builder()
        .id(b.getId())
        .status(b.getStatus())
        .bookingDate(b.getBookingDate())
        .startTime(b.getStartTime())
        .endTime(b.getEndTime())
        .note(b.getNote())
        .workerProfileId(b.getWorkerProfileId())
        .workerName(b.getWorker().getFullName())
        .workerPhone(b.getWorker().getPhoneNumber())
        .seekerName(b.getSeeker().getFullName())
        .seekerPhone(
            (b.getStatus() == BookingStatus.PENDING || b.getStatus() == BookingStatus.ACCEPTED)
                ? b.getSeeker().getPhoneNumber()
                : null)
        .createdAt(b.getCreatedAt())
        .updatedAt(b.getUpdatedAt())
        .build();
  }
}
