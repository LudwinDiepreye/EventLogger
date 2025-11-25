package org.example;

import java.time.LocalDateTime;

public class Event {
    private LocalDateTime dateTime;
    private String description;

    public Event(LocalDateTime dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }
}