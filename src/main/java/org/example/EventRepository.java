package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private static final String FILE_NAME = "events.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SEPARATOR = " — ";

    public EventRepository() throws IOException {
        ensureFileExists();
    }

    private void ensureFileExists() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public void saveEvent(Event event) throws IOException {
        String formattedDateTime = event.getDateTime().format(FORMATTER);
        String eventLine = formattedDateTime + SEPARATOR + event.getDescription();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(eventLine);
            writer.newLine();
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Event event = parseEventFromLine(line);
                    if (event != null) {
                        events.add(event);
                    }
                } catch (Exception e) {
                    System.err.println("Mistake at formating of line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Mistake at reading the file: " + e.getMessage());
        }

        return events;
    }

    private Event parseEventFromLine(String line) {
        int separatorIndex = line.indexOf(SEPARATOR);
        if (separatorIndex == -1) {
            System.err.println("Error while processing line (skipped): " + line);
            return null;
        }

        try {
            String dateTimeStr = line.substring(0, separatorIndex);
            String description = line.substring(separatorIndex + SEPARATOR.length());
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, FORMATTER);
            return new Event(dateTime, description);
        } catch (DateTimeParseException e) {
            System.err.println("Incorrect Date Format(skipped): " + line);
            return null;
        }
    }
}
