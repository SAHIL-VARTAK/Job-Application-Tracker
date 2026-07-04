package com.jobtracker.app.controller;

import com.jobtracker.app.dto.CreateJobApplicationRequest;
import com.jobtracker.app.dto.UpdateStatusRequest;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;
import com.jobtracker.app.service.JobApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService service;

    public JobApplicationController(
            JobApplicationService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<JobApplication> getAllApplications() {
        return service.getAllApplications();
    }

    @GetMapping("/{id}")
    public JobApplication getApplicationById(
            @PathVariable int id
    ) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<JobApplication> searchByCompany(
            @RequestParam String company
    ) {
        return service.searchByCompany(company);
    }

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

    @DeleteMapping("/{id}")
    public void deleteApplication(
            @PathVariable int id
    ) {
        service.deleteApplication(id);
    }

    @GetMapping("/statistics")
    public Map<ApplicationStatus, Long> getStatistics() {
        return service.getStatistics();
    }
}