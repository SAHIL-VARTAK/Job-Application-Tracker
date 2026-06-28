package com.jobtracker.app.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        String createTableQuery = loadSchema();

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

    private static String loadSchema() {
        try (
                InputStream inputStream =
                        DatabaseInitializer.class
                                .getClassLoader()
                                .getResourceAsStream("schema.sql")
        ) {
            if (inputStream == null) {
                throw new RuntimeException(
                        "schema.sql not found in resources."
                );
            }

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );
        }
        catch (IOException exception) {
            throw new RuntimeException(
                    "Failed to load schema.sql.",
                    exception
            );
        }
    }
}