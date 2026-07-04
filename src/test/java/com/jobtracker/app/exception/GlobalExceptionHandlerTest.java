package com.jobtracker.app.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void shouldHandleApplicationNotFound() {
        ApplicationNotFoundException exception =
                new ApplicationNotFoundException(999);

        ProblemDetail problem =
                handler.handleApplicationNotFound(exception);

        assertEquals(
                HttpStatus.NOT_FOUND.value(),
                problem.getStatus()
        );

        assertEquals(
                "Application Not Found",
                problem.getTitle()
        );
    }

    @Test
    void shouldHandleGenericException() {
        Exception exception =
                new RuntimeException("Something went wrong");

        ProblemDetail problem =
                handler.handleGenericException(exception);

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                problem.getStatus()
        );

        assertEquals(
                "Internal Server Error",
                problem.getTitle()
        );
    }
}