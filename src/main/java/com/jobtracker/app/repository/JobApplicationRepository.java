package com.jobtracker.app.repository;

import com.jobtracker.app.model.JobApplication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
  List<JobApplication> findAllByOrderByAppliedDateDesc();

  List<JobApplication> findByCompanyContainingIgnoreCase(String company);
}
