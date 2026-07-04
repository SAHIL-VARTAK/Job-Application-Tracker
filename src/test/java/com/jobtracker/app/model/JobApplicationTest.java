package com.jobtracker.app.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JobApplicationTest {
    @Test
    void shouldSetNotes() {
        JobApplication application = new JobApplication();

        application.setNotes("Updated notes");

        assertEquals(
                "Updated notes",
                application.getNotes()
        );
    }

    @Test
    void shouldGenerateToString() {
        JobApplication application = new JobApplication(
                1,
                "Google",
                "Software Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.of(2025, 7, 1),
                "Remote role"
        );

        String result = application.toString();

        assertTrue(result.contains("ID: 1"));
        assertTrue(result.contains("Company: Google"));
        assertTrue(result.contains("Role: Software Engineer"));
        assertTrue(result.contains("Status: APPLIED"));
        assertTrue(result.contains("Applied Date: 2025-07-01"));
        assertTrue(result.contains("Notes: Remote role"));
    }
}