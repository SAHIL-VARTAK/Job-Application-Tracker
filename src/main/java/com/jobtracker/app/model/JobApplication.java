package com.jobtracker.app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Schema(description = "Represents a job application and its recruitment progress")
@Table(name = "job_applications")
public class JobApplication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(
      description = "Unique application identifier",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Integer id;

  @Schema(description = "Company name", example = "Google")
  @Column(nullable = false)
  private String company;

  @Schema(description = "Role applied for", example = "Software Engineer")
  @Column(nullable = false)
  private String role;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Current application status", example = "APPLIED")
  @Column(nullable = false)
  private ApplicationStatus status;

  @Schema(description = "Date when the application was submitted", example = "2026-07-04")
  @Column(name = "applied_date", nullable = false)
  private LocalDate appliedDate;

  @Schema(
      description = "Additional notes about the application",
      example = "Applied via LinkedIn referral")
  private String notes;

  protected JobApplication() {}

  public JobApplication(
      Integer id,
      String company,
      String role,
      ApplicationStatus status,
      LocalDate appliedDate,
      String notes) {
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
                """
        .formatted(id, company, role, status, appliedDate, notes);
  }
}
