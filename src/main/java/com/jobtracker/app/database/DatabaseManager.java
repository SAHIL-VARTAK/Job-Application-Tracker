package com.jobtracker.app.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseManager {
    private static final String DATABASE_DIRECTORY = "data";
    private static final String DATABASE_URL = "jdbc:sqlite:data/job_tracker.db";

    private DatabaseManager() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated"
        );
    }

    public static Connection getConnection() throws SQLException {
        try {
            Files.createDirectories(Path.of(DATABASE_DIRECTORY));
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Failed to create database directory.",
                    exception
            );
        }
        return DriverManager.getConnection(DATABASE_URL);
    }
}