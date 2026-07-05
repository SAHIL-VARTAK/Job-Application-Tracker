package com.jobtracker.app.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobtracker.app.dto.CreateJobApplicationRequest;
import com.jobtracker.app.dto.UpdateStatusRequest;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;
import com.jobtracker.app.service.JobApplicationService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(JobApplicationController.class)
class JobApplicationControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private JobApplicationService service;

  @Test
  void shouldGetAllApplications() throws Exception {
    List<JobApplication> applications =
        List.of(
            new JobApplication(
                1,
                "Google",
                "Software Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                null));

    when(service.getAllApplications()).thenReturn(applications);

    mockMvc
        .perform(get("/api/applications"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].company").value("Google"));
  }

  @Test
  void shouldGetApplicationById() throws Exception {
    JobApplication application =
        new JobApplication(
            1, "Google", "Software Engineer", ApplicationStatus.APPLIED, LocalDate.now(), null);

    when(service.findById(1)).thenReturn(application);

    mockMvc
        .perform(get("/api/applications/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.company").value("Google"));
  }

  @Test
  void shouldCreateApplication() throws Exception {
    CreateJobApplicationRequest request =
        new CreateJobApplicationRequest("Google", "Software Engineer", "Applied via referral");

    JobApplication application =
        new JobApplication(
            1,
            "Google",
            "Software Engineer",
            ApplicationStatus.APPLIED,
            LocalDate.now(),
            "Applied via referral");

    when(service.addApplication(request.company(), request.role(), request.notes()))
        .thenReturn(application);

    mockMvc
        .perform(
            post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.company").value("Google"));
  }

  @Test
  void shouldUpdateStatus() throws Exception {
    UpdateStatusRequest request = new UpdateStatusRequest(ApplicationStatus.INTERVIEW);

    doNothing().when(service).updateStatus(1, ApplicationStatus.INTERVIEW);

    mockMvc
        .perform(
            put("/api/applications/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  void shouldDeleteApplication() throws Exception {
    doNothing().when(service).deleteApplication(1);

    mockMvc.perform(delete("/api/applications/1")).andExpect(status().isOk());
  }

  @Test
  void shouldReturnStatistics() throws Exception {
    Map<ApplicationStatus, Long> statistics =
        Map.of(
            ApplicationStatus.APPLIED, 2L,
            ApplicationStatus.INTERVIEW, 1L);

    when(service.getStatistics()).thenReturn(statistics);

    mockMvc
        .perform(get("/api/applications/statistics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.APPLIED").value(2));
  }

  @Test
  void shouldSearchByCompany() throws Exception {
    List<JobApplication> applications =
        List.of(
            new JobApplication(
                1,
                "Google",
                "Software Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                null));

    when(service.searchByCompany("Google")).thenReturn(applications);

    mockMvc
        .perform(get("/api/applications/search").param("company", "Google"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].company").value("Google"));
  }

  @Test
  void shouldReturnBadRequestForInvalidCreateRequest() throws Exception {
    CreateJobApplicationRequest request = new CreateJobApplicationRequest("", "", null);

    mockMvc
        .perform(
            post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Validation Error"));
  }
}
