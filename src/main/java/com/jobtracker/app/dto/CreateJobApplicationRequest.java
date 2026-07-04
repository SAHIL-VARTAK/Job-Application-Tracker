package com.jobtracker.app.dto;

public record CreateJobApplicationRequest(
        String company,
        String role,
        String notes
) {
}