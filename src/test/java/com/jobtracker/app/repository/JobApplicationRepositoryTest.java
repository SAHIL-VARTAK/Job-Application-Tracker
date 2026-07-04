package com.jobtracker.app.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(
    properties = {
      "spring.datasource.url=jdbc:sqlite:target/test_job_tracker.db",
      "spring.datasource.driver-class-name=org.sqlite.JDBC",
      "spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect",
      "spring.jpa.hibernate.ddl-auto=create-drop"
    })
class JobApplicationRepositoryTest {
  @Autowired private JobApplicationRepository repository;

  @Test
  @DisplayName("Should save and retrieve a job application")
  void shouldSaveApplication() {
    JobApplication application =
        new JobApplication(
            null,
            "Google",
            "Software Engineer",
            ApplicationStatus.APPLIED,
            LocalDate.now(),
            "Applied via referral");

    JobApplication saved = repository.save(application);

    assertNotNull(saved.getId());

    List<JobApplication> applications = repository.findAll();

    assertEquals(1, applications.size());
  }

  @Test
  @DisplayName("Should find applications by company name ignoring case")
  void shouldFindByCompanyContainingIgnoreCase() {
    repository.save(
        new JobApplication(
            null, "Google", "SDE", ApplicationStatus.APPLIED, LocalDate.now(), null));

    repository.save(
        new JobApplication(
            null, "Amazon", "SDE", ApplicationStatus.APPLIED, LocalDate.now(), null));

    List<JobApplication> applications = repository.findByCompanyContainingIgnoreCase("goo");

    assertEquals(1, applications.size());

    assertEquals("Google", applications.getFirst().getCompany());
  }

  @Test
  @DisplayName("Should return applications ordered by applied date descending")
  void shouldReturnApplicationsOrderedByDate() {
    repository.save(
        new JobApplication(
            null, "Google", "SDE", ApplicationStatus.APPLIED, LocalDate.of(2026, 7, 1), null));

    repository.save(
        new JobApplication(
            null, "Amazon", "SDE", ApplicationStatus.APPLIED, LocalDate.of(2026, 7, 2), null));

    List<JobApplication> applications = repository.findAllByOrderByAppliedDateDesc();

    assertEquals("Amazon", applications.getFirst().getCompany());

    assertEquals("Google", applications.getLast().getCompany());
  }
}
