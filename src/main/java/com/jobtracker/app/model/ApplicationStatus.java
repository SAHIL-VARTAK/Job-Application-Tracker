package com.jobtracker.app.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "Possible states of a job application"
)
public enum ApplicationStatus {
    APPLIED,
    ONLINE_ASSESSMENT,
    INTERVIEW,
    OFFER,
    REJECTED,
    ACCEPTED
}