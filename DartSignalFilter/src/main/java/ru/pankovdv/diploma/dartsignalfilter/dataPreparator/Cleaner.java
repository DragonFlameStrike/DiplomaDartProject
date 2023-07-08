package ru.pankovdv.diploma.dartsignalfilter.dataPreparator;

import org.springframework.stereotype.Component;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;
import ru.pankovdv.diploma.dartsignalfilter.domain.Segment;

import java.util.Iterator;
import java.util.List;

@Component
public class Cleaner {
    public void clearSignal(List<Measurement> signal) {
        // Первый проход: удаление записей с некорректной высотой
        Iterator<Measurement> iterator = signal.iterator();
        while (iterator.hasNext()) {
            Measurement data = iterator.next();
            if (data.getHeight() > 9000) {
                iterator.remove();
            }
        }

        // Второй проход: удаление записей с повторяющимся временем
        Double prevTime = null;
        iterator = signal.iterator();
        while (iterator.hasNext()) {
            Measurement data = iterator.next();
            Double currentTime = data.getTime();
            if (currentTime.equals(prevTime)) {
                iterator.remove();
            }
            prevTime = currentTime;
        }
    }
    public void clearSegments(List<Segment> segments){
        segments.removeIf(s -> s.getMeasurements().isEmpty() || s.getMeasurements().size() < 3);
    }
}
