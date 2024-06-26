package ru.pankovdv.diploma.dartsignalfilter.dataPreparator;

import org.springframework.stereotype.Component;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class StringDataParser implements Parser{
    @Override
    public List<Measurement> parse(String data) {
        List<Measurement> measurements = new ArrayList<>(); // List of measurements
        try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount <= 2) {
                    continue; // Skip the first two lines
                }
                String[] tokens = line.split("\\s+"); // Split the line into tokens
                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH mm ss");
                Date date = format.parse(line.substring(0, 19));
                Integer type = Integer.parseInt(tokens[6]);
                Double time = (double) date.getTime();
                Double height = Double.parseDouble(tokens[tokens.length - 1]);
                Measurement measurement = new Measurement(time, height, type);
                measurements.add(measurement);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return measurements;
    }
}
