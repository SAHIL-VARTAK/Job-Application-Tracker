package com.jobtracker.app.dto;

import com.jobtracker.app.model.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
    description = "Request payload for updating the status of a job application"
)
public record UpdateStatusRequest(
    @NotNull(message = "Status is required")
    @Schema(
        description = """
                New application status.

                Supported values:
                APPLIED,
                ONLINE_ASSESSMENT,
                INTERVIEW,
                OFFER,
                REJECTED,
                ACCEPTED
                """,
        example = "INTERVIEW"
    )
    ApplicationStatus status
) {
}