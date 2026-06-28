package com.jobtracker.app.service;

import com.jobtracker.app.database.JobApplicationDAO;
import com.jobtracker.app.exception.ApplicationNotFoundException;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobApplicationService {
    private final JobApplicationDAO jobApplicationDAO;

    public JobApplicationService() {
        this.jobApplicationDAO = new JobApplicationDAO();
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

        return jobApplicationDAO.save(application);
    }

    public List<JobApplication> getAllApplications() {
        return jobApplicationDAO.findAll();
    }

    public JobApplication findById(int id) {
        return jobApplicationDAO.findById(id)
                .orElseThrow(() ->
                        new ApplicationNotFoundException(id)
                );
    }

    public List<JobApplication> searchByCompany(String company) {
        return jobApplicationDAO.searchByCompany(company);
    }

    public void updateStatus(
            int id,
            ApplicationStatus status
    ) {
        findById(id);
        jobApplicationDAO.updateStatus(id, status);
    }

    public void deleteApplication(int id) {
        findById(id);
        jobApplicationDAO.delete(id);
    }

    public Map<ApplicationStatus, Long> getStatistics() {
        List<JobApplication> applications = getAllApplications();

        return applications.stream()
                .collect(
                        Collectors.groupingBy(
                                JobApplication::getStatus,
                                Collectors.counting()
                        )
                );
    }
}