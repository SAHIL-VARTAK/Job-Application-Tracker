package com.jobtracker.app.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {
    private DatabaseInitializer() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated"
        );
    }

    public static void initializeDatabase() {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS job_applications (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    company TEXT NOT NULL,
                    role TEXT NOT NULL,
                    status TEXT NOT NULL,
                    applied_date TEXT NOT NULL,
                    notes TEXT
                );
                """;

        try (
                Connection connection = DatabaseManager.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute(createTableQuery);
        }
        catch (SQLException exception) {
            throw new RuntimeException(
                    "Failed to initialize database.",
                    exception
            );
        }
    }
}