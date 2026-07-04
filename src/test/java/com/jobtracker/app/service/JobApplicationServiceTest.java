package com.jobtracker.app.service;

import com.jobtracker.app.exception.ApplicationNotFoundException;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;
import com.jobtracker.app.repository.JobApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {
    @Mock
    private JobApplicationRepository repository;

    @InjectMocks
    private JobApplicationService service;

    @Test
    void shouldAddApplication() {
        JobApplication application =
                new JobApplication(
                        1,
                        "Google",
                        "Software Engineer",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        "Applied via referral"
                );

        when(repository.save(any(JobApplication.class)))
                .thenReturn(application);

        JobApplication result =
                service.addApplication(
                        "Google",
                        "Software Engineer",
                        "Applied via referral"
                );

        assertNotNull(result);
        assertEquals(
                "Google",
                result.getCompany()
        );

        verify(repository).save(any(JobApplication.class));
    }

    @Test
    void shouldReturnAllApplications() {
        List<JobApplication> applications =
                List.of(
                        new JobApplication(
                                1,
                                "Google",
                                "Software Engineer",
                                ApplicationStatus.APPLIED,
                                LocalDate.now(),
                                null
                        )
                );

        when(repository.findAllByOrderByAppliedDateDesc())
                .thenReturn(applications);

        List<JobApplication> result =
                service.getAllApplications();

        assertEquals(1, result.size());

        verify(repository)
                .findAllByOrderByAppliedDateDesc();
    }

    @Test
    void shouldFindApplicationById() {
        JobApplication application =
                new JobApplication(
                        1,
                        "Google",
                        "Software Engineer",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        null
                );

        when(repository.findById(1))
                .thenReturn(Optional.of(application));

        JobApplication result =
                service.findById(1);

        assertEquals(
                "Google",
                result.getCompany()
        );
    }

    @Test
    void shouldThrowExceptionWhenApplicationNotFound() {
        when(repository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ApplicationNotFoundException.class,
                () -> service.findById(999)
        );
    }

    @Test
    void shouldUpdateStatus() {
        JobApplication application =
                new JobApplication(
                        1,
                        "Google",
                        "Software Engineer",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        null
                );

        when(repository.findById(1))
                .thenReturn(Optional.of(application));

        service.updateStatus(
                1,
                ApplicationStatus.INTERVIEW
        );

        assertEquals(
                ApplicationStatus.INTERVIEW,
                application.getStatus()
        );

        verify(repository).save(application);
    }

    @Test
    void shouldDeleteApplication() {
        JobApplication application =
                new JobApplication(
                        1,
                        "Google",
                        "Software Engineer",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        null
                );

        when(repository.findById(1))
                .thenReturn(Optional.of(application));

        service.deleteApplication(1);

        verify(repository).delete(application);
    }

    @Test
    void shouldReturnStatistics() {
        List<JobApplication> applications =
                List.of(
                        new JobApplication(
                                1,
                                "Google",
                                "SDE",
                                ApplicationStatus.APPLIED,
                                LocalDate.now(),
                                null
                        ),
                        new JobApplication(
                                2,
                                "Amazon",
                                "SDE",
                                ApplicationStatus.INTERVIEW,
                                LocalDate.now(),
                                null
                        )
                );

        when(repository.findAllByOrderByAppliedDateDesc())
                .thenReturn(applications);

        var statistics =
                service.getStatistics();

        assertEquals(
                1L,
                statistics.get(ApplicationStatus.APPLIED)
        );

        assertEquals(
                1L,
                statistics.get(ApplicationStatus.INTERVIEW)
        );
    }

    @Test
    void shouldThrowExceptionWhenDeletingUnknownApplication() {
        when(repository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ApplicationNotFoundException.class,
                () -> service.deleteApplication(999)
        );
    }

    @Test
    void shouldThrowExceptionWhenUpdatingUnknownApplication() {
        when(repository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ApplicationNotFoundException.class,
                () -> service.updateStatus(
                        999,
                        ApplicationStatus.INTERVIEW
                )
        );
    }

    @Test
    void shouldSearchApplicationsByCompany() {
        List<JobApplication> applications = List.of(
                new JobApplication(
                        1,
                        "Google",
                        "Software Engineer",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        "Remote"
                )
        );

        when(repository.findByCompanyContainingIgnoreCase("goo"))
                .thenReturn(applications);

        List<JobApplication> result =
                service.searchByCompany("goo");

        assertEquals(1, result.size());
        assertEquals("Google", result.getFirst().getCompany());

        verify(repository)
                .findByCompanyContainingIgnoreCase("goo");
    }
}