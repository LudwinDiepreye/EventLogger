package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class EventController {
    private EventRepository repository;
    private EventView view;

    public EventController(EventRepository repository, EventView view) {
        this.repository = repository;
        this.view = view;
    }

    public void addEvent(String description) {
        try {
            Event event = new Event(LocalDateTime.now(), description);
            repository.saveEvent(event);
            view.showSuccessMessage("Event Logged Successfully!");
        } catch (IOException e) {
            view.showErrorMessage("Mistake at logging event: " + e.getMessage());
        }
    }

    public void showStatistics() {
        List<Event> events = repository.getAllEvents();

        if (events.isEmpty()) {
            view.showNoEventsMessage();
            return;
        }

        int totalEvents = events.size();

        LocalDate today = LocalDate.now();
        long eventsToday = events.stream()
                .filter(event -> event.getDateTime().toLocalDate().equals(today))
                .count();

        Event firstEvent = events.get(0);

        view.showStatistics(totalEvents, (int) eventsToday, firstEvent);
    }

    public void showEventsByDate(String dateStr) {
        try {
            LocalDate targetDate = LocalDate.parse(dateStr);
            List<Event> allEvents = repository.getAllEvents();

            List<Event> filteredEvents = allEvents.stream()
                    .filter(event -> event.getDateTime().toLocalDate().equals(targetDate))
                    .collect(Collectors.toList());

            view.showEventsByDate(dateStr, filteredEvents);

        } catch (DateTimeParseException e) {
            view.showErrorMessage("Wrong date format.Please use: yyyy-MM-dd");
        }
    }
}
