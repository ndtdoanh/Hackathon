package com.hacof.hackathon.util;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomLocalDateTimeDeserialized extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER_WITH_MILLIS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final DateTimeFormatter FORMATTER_NO_MILLIS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();

        // Remove Z if present
        if (date.endsWith("Z")) {
            date = date.substring(0, date.length() - 1);
        }

        // Padding for milliseconds
        String[] parts = date.split("T");
        if (parts.length != 2) {
            throw new IOException("Invalid datetime format");
        }

        String[] dateParts = parts[0].split("-");
        if (dateParts.length != 3) {
            throw new IOException("Invalid date format");
        }

        String year = dateParts[0];
        String month = String.format("%02d", Integer.parseInt(dateParts[1]));
        String day = String.format("%02d", Integer.parseInt(dateParts[2]));

        String rebuiltDate = year + "-" + month + "-" + day + "T" + parts[1];

        try {
            if (rebuiltDate.contains(".")) {
                return LocalDateTime.parse(rebuiltDate, FORMATTER_WITH_MILLIS);
            } else {
                return LocalDateTime.parse(rebuiltDate, FORMATTER_NO_MILLIS);
            }
        } catch (DateTimeParseException e) {
            throw new IOException("Failed to parse LocalDateTime: " + rebuiltDate, e);
        }
    }
}
