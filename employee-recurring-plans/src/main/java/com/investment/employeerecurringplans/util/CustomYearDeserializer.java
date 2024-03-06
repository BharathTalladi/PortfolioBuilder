package com.investment.employeerecurringplans.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomYearDeserializer extends JsonDeserializer<Date> {

    private final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String yearStr = jsonParser.getText();
        try {
            return yearFormat.parse(yearStr);
        } catch (ParseException e) {
            throw new IOException("Failed to parse date: " + yearStr, e);
        }
    }
}
