package lk.wedalk.disputes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Long requestId;

    @NotBlank(message = "Reason is required")
    private String reason;
}
