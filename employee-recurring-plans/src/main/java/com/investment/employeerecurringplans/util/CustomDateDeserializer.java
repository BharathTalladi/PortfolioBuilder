package com.investment.employeerecurringplans.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    // SimpleDateFormat to parse date from JSON string
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // Get the date as a string from the JSON parser
        String dateStr = jsonParser.getText();
        try {
            // Parse the date string to a Date object using SimpleDateFormat
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            // Throw IOException if parsing fails
            throw new IOException("Failed to parse date: " + dateStr, e);
        }
    }
}
