package com.jobtracker.app;

import com.jobtracker.app.model.ApplicationStatus;
import com.jobtracker.app.service.JobApplicationService;
import com.jobtracker.app.util.ConsoleHelper;

public class JobTrackerApplication {
    public static void main(String[] args) {
        JobApplicationService service = new JobApplicationService();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = ConsoleHelper.readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> addApplication(service);
                    case 2 -> viewApplications(service);
                    case 3 -> searchByCompany(service);
                    case 4 -> updateStatus(service);
                    case 5 -> deleteApplication(service);
                    case 6 -> service.showStatistics();
                    case 0 -> running = false;

                    default -> System.out.println("Invalid option.");
                }
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("""
                \n===== JOB APPLICATION TRACKER =====
                1. Add Application
                2. View Applications
                3. Search By Company
                4. Update Status
                5. Delete Application
                6. View Statistics
                
                0. Exit
                """);
    }

    private static void addApplication(
            JobApplicationService service
    ) {
        String company = ConsoleHelper.readString("Company: ");
        String role = ConsoleHelper.readString("Role: ");
        String notes = ConsoleHelper.readString("Notes: ");

        service.addApplication(company, role, notes);

        System.out.println("Application added successfully.");
    }

    private static void viewApplications(
            JobApplicationService service
    ) {
        if (service.getAllApplications().isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        service.getAllApplications().forEach(System.out::println);
    }

    private static void searchByCompany(
            JobApplicationService service
    ) {
        String company = ConsoleHelper.readString("Enter company name: ");

        var applications = service.searchByCompany(company);

        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        applications.forEach(System.out::println);
    }

    public static ApplicationStatus readStatus() {
        System.out.println("\nAvailable Statuses:");

        for (ApplicationStatus status : ApplicationStatus.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status);
        }

        while (true) {
            int choice = ConsoleHelper.readInt("Choose status: ");

            if (choice >= 1 && choice <= ApplicationStatus.values().length) {
                return ApplicationStatus.values()[choice - 1];
            }

            System.out.println("Invalid status.");
        }
    }

    private static void updateStatus(
            JobApplicationService service
    ) {
        int id = ConsoleHelper.readInt("Application ID: ");

        ApplicationStatus status = ConsoleHelper.readStatus();
        service.updateStatus(id, status);

        System.out.println("Status updated successfully.");
    }

    private static void deleteApplication(
            JobApplicationService service
    ) {
        int id = ConsoleHelper.readInt("Application ID: ");
        service.deleteApplication(id);

        System.out.println("Application deleted successfully.");
    }
}