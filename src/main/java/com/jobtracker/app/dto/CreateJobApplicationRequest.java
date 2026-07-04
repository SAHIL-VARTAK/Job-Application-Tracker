package com.jobtracker.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
    description = "Request payload for creating a new job application"
)
public record CreateJobApplicationRequest(
    @NotBlank(message = "Company name is required")
    @Size(
        max = 100,
        message = "Company name cannot exceed 100 characters"
    )
    @Schema(
        description = "Company name",
        example = "Google"
    )
    String company,

    @NotBlank(message = "Role is required")
    @Size(
        max = 100,
        message = "Role cannot exceed 100 characters"
    )
    @Schema(
        description = "Role applied for",
        example = "Software Engineer"
    )
    String role,

    @Size(
        max = 500,
        message = "Notes cannot exceed 500 characters"
    )
    @Schema(
        description = "Additional notes about the application",
        example = "Applied via employee referral"
    )
    String notes
) {
}