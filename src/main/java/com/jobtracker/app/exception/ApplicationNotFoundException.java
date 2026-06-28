package com.jobtracker.app.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(int id) {
        super("Application with ID " + id + " was not found.");
    }
}