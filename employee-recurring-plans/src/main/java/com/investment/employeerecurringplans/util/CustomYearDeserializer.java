package com.investment.employeerecurringplans.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomYearDeserializer extends JsonDeserializer<Date> {

    // SimpleDateFormat to parse year from JSON string
    private final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // Get the year as a string from the JSON parser
        String yearStr = jsonParser.getText();
        try {
            // Parse the year string to a Date object using SimpleDateFormat
            return yearFormat.parse(yearStr);
        } catch (ParseException e) {
            // Throw IOException if parsing fails
            throw new IOException("Failed to parse date: " + yearStr, e);
        }
    }
}
