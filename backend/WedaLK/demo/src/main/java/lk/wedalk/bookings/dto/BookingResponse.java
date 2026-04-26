package lk.wedalk.bookings.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lk.wedalk.common.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

  private Long id;
  private BookingStatus status;
  private LocalDate bookingDate;
  private LocalTime startTime;
  private LocalTime endTime;
  private String note;

  private Long workerProfileId;
  private String workerName;
  /** Visible to seeker only when status is ACCEPTED (or after confirmation). */
  private String workerPhone;

  private String seekerName;
  /** Visible to worker for PENDING / ACCEPTED so they can coordinate. */
  private String seekerPhone;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
