package ru.pankovdv.diploma.dartsignalfilter.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


import java.io.IOException;

@Converter
public class MeasurementConverter implements AttributeConverter<Measurement, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Measurement measurement) {
        try {
            return objectMapper.writeValueAsString(measurement);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Measurement convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Measurement.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

