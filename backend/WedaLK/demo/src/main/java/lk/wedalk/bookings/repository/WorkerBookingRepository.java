package lk.wedalk.bookings.repository;

import java.time.LocalDate;
import java.util.List;
import lk.wedalk.bookings.model.WorkerBooking;
import lk.wedalk.common.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerBookingRepository extends JpaRepository<WorkerBooking, Long> {

  List<WorkerBooking> findBySeeker_IdOrderByBookingDateDescStartTimeDesc(Long seekerId);

  List<WorkerBooking> findByWorker_IdOrderByBookingDateDescStartTimeDesc(Long workerId);

  List<WorkerBooking> findByWorker_IdAndBookingDateAndStatusIn(
      Long workerId, LocalDate bookingDate, List<BookingStatus> statuses);
}
