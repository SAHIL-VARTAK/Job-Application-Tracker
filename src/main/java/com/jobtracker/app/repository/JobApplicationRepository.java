package com.jobtracker.app.repository;

import com.jobtracker.app.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Integer> {
    List<JobApplication> findAllByOrderByAppliedDateDesc();

    List<JobApplication> findByCompanyContainingIgnoreCase(
            String company
    );
}