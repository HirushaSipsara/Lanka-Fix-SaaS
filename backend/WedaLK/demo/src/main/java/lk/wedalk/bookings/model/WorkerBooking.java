package lk.wedalk.bookings.model;

import jakarta.persistence.*;
import lk.wedalk.common.enums.BookingStatus;
import lk.wedalk.users.model.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "worker_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerBooking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "seeker_id", nullable = false)
  private User seeker;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "worker_id", nullable = false)
  private User worker;

  @Column(name = "worker_profile_id", nullable = false)
  private Long workerProfileId;

  @Column(nullable = false)
  private LocalDate bookingDate;

  @Column(nullable = false)
  private LocalTime startTime;

  @Column(nullable = false)
  private LocalTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @Builder.Default
  private BookingStatus status = BookingStatus.PENDING;

  @Column(length = 500)
  private String note;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
