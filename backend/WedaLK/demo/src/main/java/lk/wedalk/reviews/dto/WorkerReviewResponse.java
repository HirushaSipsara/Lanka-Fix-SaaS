package lk.wedalk.reviews.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lightweight worker review response for public worker review listing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerReviewResponse {

    private String reviewerName;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
