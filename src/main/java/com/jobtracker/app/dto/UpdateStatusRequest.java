package com.jobtracker.app.dto;

import com.jobtracker.app.model.ApplicationStatus;

public record UpdateStatusRequest(
        ApplicationStatus status
) {
}