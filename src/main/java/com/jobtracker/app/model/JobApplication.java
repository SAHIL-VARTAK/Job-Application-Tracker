package com.jobtracker.app.model;

import java.time.LocalDate;

public class JobApplication {
    private Integer id;
    private final String company;
    private final String role;
    private ApplicationStatus status;
    private final LocalDate appliedDate;
    private String notes;

    public JobApplication(
            Integer id,
            String company,
            String role,
            ApplicationStatus status,
            LocalDate appliedDate,
            String notes
    ) {
        this.id = id;
        this.company = company;
        this.role = role;
        this.status = status;
        this.appliedDate = appliedDate;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return """
                --------------------------------
                ID: %d
                Company: %s
                Role: %s
                Status: %s
                Applied Date: %s
                Notes: %s
                --------------------------------
                """.formatted(
                id,
                company,
                role,
                status,
                appliedDate,
                notes
        );
    }
}