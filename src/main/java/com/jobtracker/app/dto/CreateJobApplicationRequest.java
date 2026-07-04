package com.jobtracker.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "Request payload for creating a new job application"
)
public record CreateJobApplicationRequest(
    @Schema(
        description = "Company name",
        example = "Google"
    )
    String company,

    @Schema(
        description = "Role applied for",
        example = "Software Engineer"
    )
    String role,

    @Schema(
        description = "Additional notes about the application",
        example = "Applied via employee referral"
    )
    String notes
) {
}