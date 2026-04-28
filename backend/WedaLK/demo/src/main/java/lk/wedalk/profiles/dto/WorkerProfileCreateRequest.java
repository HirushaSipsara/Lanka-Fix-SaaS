package lk.wedalk.profiles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerProfileCreateRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Contact number is required")
    @Size(max = 20, message = "Contact number must not exceed 20 characters")
    private String contactNumber;

    @NotBlank(message = "Bio is required")
    @Size(max = 2000, message = "Bio must not exceed 2000 characters")
    private String bio;

    @Size(max = 5000, message = "Profile picture value is too large")
    private String profilePictureUrl;

    @NotEmpty(message = "At least one skill is required")
    private List<String> skills;

    @NotBlank(message = "District is required")
    @Size(max = 100, message = "District must not exceed 100 characters")
    private String district;

    @NotEmpty(message = "At least one service area is required")
    private List<String> serviceAreas;

    @Positive(message = "Hourly rate must be greater than zero")
    private double hourlyRate;

    @NotBlank(message = "Availability is required")
    @Size(max = 100, message = "Availability must not exceed 100 characters")
    private String availability;
}
