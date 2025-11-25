package org.example;

import java.io.IOException;

/**
 * Project done by Ludwin Diepreye Olali Group KND-24
 * ludwindiepreye@gmail.com
 */
public class EventLogger {
    public static void main(String[] args) {
        EventView view = new EventView();

        try {
            EventRepository repository = new EventRepository();
            EventController controller = new EventController(repository, view);

            // Getting the description of the event from the user
            String eventDescription = view.promptForEventDescription();

            // Adding of events
            controller.addEvent(eventDescription);

            // Showing of Statistics
            controller.showStatistics();

            // Additional function to find events based on the date
            if (view.promptForDateSearch()) {
                String dateStr = view.promptForDate();
                controller.showEventsByDate(dateStr);
            }

        } catch (IOException e) {
            view.showErrorMessage("Mistake at Initialization: " + e.getMessage());
        } finally {
            view.close();
        }
    }
}