package com.jobtracker.app.database;

import com.jobtracker.app.exception.ApplicationNotFoundException;
import com.jobtracker.app.exception.DatabaseException;
import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.model.JobApplication;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobApplicationDAO {
    public JobApplication save(JobApplication application) {
        String sql = """
            INSERT INTO job_applications (
                company,
                role,
                status,
                applied_date,
                notes
            )
            VALUES (?, ?, ?, ?, ?)
            """;

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            statement.setString(1, application.getCompany());
            statement.setString(2, application.getRole());
            statement.setString(3, application.getStatus().name());
            statement.setString(4, application.getAppliedDate().toString());
            statement.setString(5, application.getNotes());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                application.setId(keys.getInt(1));
            }

            return application;
        }
        catch (SQLException exception) {
            throw new DatabaseException(
                    "Failed to save application.",
                    exception
            );
        }
    }

    private JobApplication mapRow(ResultSet resultSet) throws SQLException {
        return new JobApplication(
                resultSet.getInt("id"),
                resultSet.getString("company"),
                resultSet.getString("role"),
                ApplicationStatus.valueOf(resultSet.getString("status")),
                LocalDate.parse(resultSet.getString("applied_date")),
                resultSet.getString("notes")
        );
    }

    public List<JobApplication> findAll() {
        String sql = """
            SELECT *
            FROM job_applications
            ORDER BY applied_date DESC
            """;

        List<JobApplication> applications = new ArrayList<>();

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                applications.add(mapRow(resultSet));
            }

            return applications;
        }
        catch (SQLException exception) {
            throw new DatabaseException(
                    "Failed to fetch applications.",
                    exception
            );
        }
    }

    public Optional<JobApplication> findById(int id) {
        String sql = """
            SELECT *
            FROM job_applications
            WHERE id = ?
            """;

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapRow(resultSet));
            }

            return Optional.empty();
        }
        catch (SQLException exception) {
            throw new DatabaseException(
                    "Failed to fetch application.",
                    exception
            );
        }
    }

    public void updateStatus(
            int id,
            ApplicationStatus status
    ) {
        String sql = """
            UPDATE job_applications
            SET status = ?
            WHERE id = ?
            """;

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, status.name());
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new ApplicationNotFoundException(id);
            }
        }
        catch (SQLException exception) {
            throw new DatabaseException(
                    "Failed to update application status.",
                    exception
            );
        }
    }

    public void delete(int id) {
        String sql = """
            DELETE
            FROM job_applications
            WHERE id = ?
            """;

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new ApplicationNotFoundException(id);
            }
        }
        catch (SQLException exception) {
            throw new DatabaseException(
                    "Failed to delete application.",
                    exception
            );
        }
    }
}
