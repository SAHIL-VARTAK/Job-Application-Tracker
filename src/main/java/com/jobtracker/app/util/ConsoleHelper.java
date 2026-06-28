package com.jobtracker.app.util;

import com.jobtracker.app.model.ApplicationStatus;

import java.util.Scanner;

public class ConsoleHelper {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleHelper() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated"
        );
    }

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(SCANNER.nextLine());
            }
            catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static String readString(String message) {
        System.out.print(message);

        return SCANNER.nextLine().trim();
    }

    public static ApplicationStatus readStatus() {
        System.out.println("\nAvailable Statuses:");

        ApplicationStatus[] statuses = ApplicationStatus.values();

        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }

        while (true) {
            int choice = readInt("Choose status: ");

            if (choice >= 1 && choice <= statuses.length) {
                return statuses[choice - 1];
            }

            System.out.println("Invalid status. Try again.");
        }
    }
}