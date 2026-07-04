package com.jobtracker.app.service;

import com.jobtracker.app.exception.ApplicationNotFoundException;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;
import com.jobtracker.app.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {
    private final JobApplicationRepository repository;

    public JobApplicationService(
            JobApplicationRepository repository
    ) {
        this.repository = repository;
    }

    public JobApplication addApplication(
            String company,
            String role,
            String notes
    ) {
        JobApplication application =
                new JobApplication(
                        null,
                        company,
                        role,
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        notes
                );

        return repository.save(application);
    }

    public List<JobApplication> getAllApplications() {
        return repository.findAllByOrderByAppliedDateDesc();
    }

    public JobApplication findById(int id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ApplicationNotFoundException(id)
                );
    }

    public List<JobApplication> searchByCompany(
            String company
    ) {
        return repository
                .findByCompanyContainingIgnoreCase(company);
    }

    public void updateStatus(
            int id,
            ApplicationStatus status
    ) {
        JobApplication application = findById(id);

        application.setStatus(status);

        repository.save(application);
    }

    public void deleteApplication(int id) {
        JobApplication application = findById(id);

        repository.delete(application);
    }

    public Map<ApplicationStatus, Long> getStatistics() {
        List<JobApplication> applications =
                getAllApplications();

        return applications.stream()
                .collect(
                        Collectors.groupingBy(
                                JobApplication::getStatus,
                                Collectors.counting()
                        )
                );
    }
}