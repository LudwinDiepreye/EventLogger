package org.example;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EventView {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SEPARATOR = " — ";
    private Scanner scanner;

    public EventView() {
        this.scanner = new Scanner(System.in);
    }

    public String promptForEventDescription() {
        System.out.println("=== Event Logger ===");
        System.out.print("Please write the Event you want to log: ");
        return scanner.nextLine();
    }

    public boolean promptForDateSearch() {
        System.out.print("\nWould you like to see events by a specific date? (y/n): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y");
    }

    public String promptForDate() {
        System.out.print("Please enter a specific date: (yyyy-MM-dd): ");
        return scanner.nextLine();
    }

    public void showSuccessMessage(String message) {
        System.out.println("✓ " + message);
    }

    public void showErrorMessage(String message) {
        System.err.println("✗ " + message);
    }

    public void showNoEventsMessage() {
        System.out.println("\n=== Statistics ===");
        System.out.println("There are no logged Events.");
    }

    public void showStatistics(int totalEvents, int eventsToday, Event firstEvent) {
        System.out.println("\n=== Statistics ===");
        System.out.println("Total Events: " + totalEvents);
        System.out.println("Events Today: " + eventsToday);
        System.out.println("First Event: " + firstEvent.getDateTime().format(FORMATTER) +
                SEPARATOR + firstEvent.getDescription());
    }

    public void showEventsByDate(String date, List<Event> events) {
        System.out.println("\n=== Event at " + date + " ===");
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            for (Event event : events) {
                System.out.println(event.getDateTime().format(FORMATTER) +
                        SEPARATOR + event.getDescription());
            }
        }
    }

    public void close() {
        scanner.close();
    }
}