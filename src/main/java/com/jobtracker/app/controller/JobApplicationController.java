package com.jobtracker.app.controller;

import com.jobtracker.app.dto.CreateJobApplicationRequest;
import com.jobtracker.app.dto.UpdateStatusRequest;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;
import com.jobtracker.app.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(
    name = "Job Applications",
    description = "APIs for managing job applications and recruitment progress"
)
@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService service;

    public JobApplicationController(
            JobApplicationService service
    ) {
        this.service = service;
    }

    @Operation(
        summary = "Get all applications",
        description = "Returns all job applications sorted by applied date in descending order."
    )
    @GetMapping
    public List<JobApplication> getAllApplications() {
        return service.getAllApplications();
    }

    @Operation(
        summary = "Get application by ID",
        description = "Returns a job application for the specified ID."
    )
    @GetMapping("/{id}")
    public JobApplication getApplicationById(
            @PathVariable int id
    ) {
        return service.findById(id);
    }

    @Operation(
        summary = "Search applications by company",
        description = "Returns all job applications whose company name contains the provided keyword."
    )
    @GetMapping("/search")
    public List<JobApplication> searchByCompany(
            @RequestParam String company
    ) {
        return service.searchByCompany(company);
    }

    @Operation(
        summary = "Create a new application",
        description = "Creates a new job application with APPLIED status and the current date."
    )
    @PostMapping
    public JobApplication createApplication(
            @RequestBody CreateJobApplicationRequest request
    ) {
        return service.addApplication(
                request.company(),
                request.role(),
                request.notes()
        );
    }

    @Operation(
        summary = "Update application status",
        description = "Updates the status of an existing job application."
    )
    @PutMapping("/{id}/status")
    public void updateStatus(
            @PathVariable int id,
            @RequestBody UpdateStatusRequest request
    ) {
        service.updateStatus(
                id,
                request.status()
        );
    }

    @Operation(
        summary = "Delete an application",
        description = "Deletes the job application associated with the specified ID."
    )
    @DeleteMapping("/{id}")
    public void deleteApplication(
            @PathVariable int id
    ) {
        service.deleteApplication(id);
    }

    @Operation(
        summary = "Get application statistics",
        description = "Returns the total number of applications grouped by their current status."
    )
    @GetMapping("/statistics")
    public Map<ApplicationStatus, Long> getStatistics() {
        return service.getStatistics();
    }
}