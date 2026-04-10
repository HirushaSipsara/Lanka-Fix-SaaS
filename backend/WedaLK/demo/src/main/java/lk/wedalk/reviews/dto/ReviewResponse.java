package lk.wedalk.reviews.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ReviewResponse.java — Review Response DTO
 *
 * <p>Returned to clients after review creation or retrieval.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private Long requestId;
    private Long reviewerId;
    private String reviewerName;
    private Long revieweeId;
    private String revieweeName;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
