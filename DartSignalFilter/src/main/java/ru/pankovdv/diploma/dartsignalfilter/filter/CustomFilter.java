package ru.pankovdv.diploma.dartsignalfilter.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pankovdv.diploma.dartsignalfilter.config.DSFConfig;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;
import ru.pankovdv.diploma.dartsignalfilter.domain.Segment;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomFilter {

    @Autowired
    DSFConfig config;

    public List<Segment> filter(List<Segment> filteredSegments) {

        if(!config.getAproxiEnable()) return filteredSegments;

        List<Segment> out = new ArrayList<>();
        for (Segment segment : filteredSegments) {
            List<Measurement> currentMeasurements = segment.getMeasurements();
            List<Measurement> filteredMeasurements = new ArrayList<>();
            List<Integer> extremumIndexHeightList = new ArrayList<>();
            List<Integer> medianIndexHeightList = new ArrayList<>();

            extremumIndexHeightList.add(0);
            for (int currentMeasurement = 1; currentMeasurement < currentMeasurements.size() - 1; currentMeasurement++) {

                var prevM = currentMeasurements.get(currentMeasurement - 1);
                var currM = currentMeasurements.get(currentMeasurement);
                var nextM = currentMeasurements.get(currentMeasurement + 1);


                if (currM.getHeight() > prevM.getHeight() && currM.getHeight() > nextM.getHeight()) {
                    // Экстремум - максимум
                    extremumIndexHeightList.add(currentMeasurement);
                } else if (currM.getHeight() < prevM.getHeight() && currM.getHeight() < nextM.getHeight()) {
                    // Экстремум - минимум
                    extremumIndexHeightList.add(currentMeasurement);
                }
            }
            extremumIndexHeightList.add(currentMeasurements.size() - 1);

            medianIndexHeightList.add(0);
            for(int i = 0;i<extremumIndexHeightList.size()-1;i++) {
                var currP = extremumIndexHeightList.get(i);
                var nextP = extremumIndexHeightList.get(i+1);

                Integer medianP = nextP - (nextP-currP)/2;
                medianIndexHeightList.add(medianP);
            }
            medianIndexHeightList.add(currentMeasurements.size() - 1);

            for (int i = 0; i < medianIndexHeightList.size() - 1; i++) {
                int startIndex = medianIndexHeightList.get(i);
                int endIndex = medianIndexHeightList.get(i + 1);

                double startHeight = currentMeasurements.get(startIndex).getHeight();
                double endHeight = currentMeasurements.get(endIndex).getHeight();

                double slope = (endHeight - startHeight) / (endIndex - startIndex);

                filteredMeasurements.add(currentMeasurements.get(startIndex));
                for (int j = startIndex + 1; j < endIndex; j++) {
                    var currM = currentMeasurements.get(j);

                    double interpolatedHeight = startHeight + slope * (j - startIndex);
                    currM.setHeight(interpolatedHeight);
                    filteredMeasurements.add(currM);
                }
            }
            filteredMeasurements.add(currentMeasurements.get(currentMeasurements.size() - 1));

            Segment newSegment = new Segment(filteredMeasurements,segment.getFirstIndex(),segment.getLastIndex());
            out.add(newSegment);
        }
        return out;
    }

}

