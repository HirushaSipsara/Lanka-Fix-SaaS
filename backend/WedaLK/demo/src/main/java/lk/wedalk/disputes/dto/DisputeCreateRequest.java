package lk.wedalk.disputes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DisputeCreateRequest.java — Create Dispute DTO
 *
 * <p>Triggered when a seeker marks a request as "Not Completed".
 * This creates a dispute for admin review.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisputeCreateRequest {

    @NotNull(message = "Request ID is required")
    @Positive(message = "Request ID must be valid")
    private Long requestId;

    @NotBlank(message = "Reason is required")
    @Size(max = 1000, message = "Reason must be 1000 characters or fewer")
    private String reason;
}
