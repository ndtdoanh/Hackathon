package com.hacof.hackathon.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomLocalDateTimeDeserialized extends JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"); // Without trailing fractions

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        try {
            // Preprocess the input string to remove the extra period and fractional parts
            if (date.contains(".")) {
                date = date.substring(0, date.indexOf(".")); // Take only the valid part before the first '.'
            }
            // Parse the cleaned-up string
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid date format: " + date, e);
        }
    }
}
