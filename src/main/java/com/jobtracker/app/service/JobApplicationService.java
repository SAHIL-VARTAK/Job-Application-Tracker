package com.jobtracker.app.service;

import com.jobtracker.app.exception.ApplicationNotFoundException;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobApplicationService {
    private final List<JobApplication> applications;
    private int nextId;

    public JobApplicationService() {
        this.applications = new ArrayList<>();
        this.nextId = 1;
    }

    public JobApplication addApplication(
            String company,
            String role,
            String notes
    ) {
        JobApplication application = new JobApplication(
                nextId++,
                company,
                role,
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                notes
        );

        applications.add(application);

        return application;
    }

    public List<JobApplication> getAllApplications() {
        return applications;
    }

    public JobApplication findById(int id) {
        return applications.stream()
                .filter(application -> application.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public List<JobApplication> searchByCompany(String company) {
        return applications.stream()
                .filter(application ->
                        application.getCompany()
                                .equalsIgnoreCase(company))
                .toList();
    }

    public void updateStatus(
            int id,
            ApplicationStatus status
    ) {
        JobApplication application = findById(id);
        application.setStatus(status);
    }

    public void deleteApplication(int id) {
        JobApplication application = findById(id);
        applications.remove(application);
    }

    public void showStatistics() {
        System.out.println("\n===== STATISTICS =====");
        System.out.println("Total Applications: " + applications.size());

        Map<ApplicationStatus, Long> statistics =
                applications.stream()
                        .collect(
                                Collectors.groupingBy(
                                        JobApplication::getStatus,
                                        Collectors.counting()
                                )
                        );

        for (ApplicationStatus status : ApplicationStatus.values()) {
            long count = statistics.getOrDefault(status, 0L);

            System.out.println(status + ": " + count);
        }
    }
}