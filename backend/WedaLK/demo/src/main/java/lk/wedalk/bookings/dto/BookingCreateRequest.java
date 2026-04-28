package lk.wedalk.bookings.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreateRequest {

  @NotNull(message = "Worker profile is required")
  @Positive(message = "Worker profile must be a valid id")
  private Long workerProfileId;

  @NotNull(message = "Date is required")
  @FutureOrPresent(message = "Booking date cannot be in the past")
  private LocalDate bookingDate;

  @NotNull(message = "Start time is required")
  private LocalTime startTime;

  @NotNull(message = "End time is required")
  private LocalTime endTime;

  @Size(max = 500, message = "Note must be at most 500 characters")
  private String note;

  @AssertTrue(message = "End time must be after start time")
  public boolean isTimeRangeValid() {
    if (startTime == null || endTime == null) return true;
    return endTime.isAfter(startTime);
  }
}
