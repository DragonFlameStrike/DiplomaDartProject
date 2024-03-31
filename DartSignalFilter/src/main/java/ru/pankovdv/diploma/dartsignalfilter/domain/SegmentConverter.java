package ru.pankovdv.diploma.dartsignalfilter.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class SegmentConverter implements AttributeConverter<Segment, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Segment segment) {
        try {
            return objectMapper.writeValueAsString(segment);
        } catch (JsonProcessingException e) {
            // Обработка ошибок
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Segment convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Segment.class);
        } catch (JsonProcessingException e) {
            // Обработка ошибок
            e.printStackTrace();
            return null;
        }
    }
}
