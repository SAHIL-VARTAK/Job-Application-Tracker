package com.jobtracker.app.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(name = "applied_date", nullable = false)
    private LocalDate appliedDate;

    private String notes;

    protected JobApplication() {
    }

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