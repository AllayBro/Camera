package org.example.camera.core;

import org.example.camera.rest.DataProvider;
import org.example.camera.soap.journal.dto.JournalRecord;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JournalCoreService {

    private static final JournalCoreService INSTANCE = new JournalCoreService();
    
    private JournalCoreService() {}

    public List<JournalRecord> getDroneJournal(String droneId) {

        List<Map<String, Object>> photos;
        List<Map<String, Object>> targets;

        try {
            DataProvider provider = new DataProvider();
            photos = provider.getPhotos();
            targets = provider.getTargets();
        } catch (Exception e) {
            // Возвращаем пустой список при ошибке загрузки XML
            return Collections.emptyList();
        }

        List<JournalRecord> result = new ArrayList<>();

        for (Map<String, Object> photo : photos) {

            Object photoDroneId = photo.get("droneId");
            if (photoDroneId == null || !photoDroneId.equals(droneId)) {
                continue;
            }

            String targetId = (String) photo.get("targetId");

            Map<String, Object> target = targets.stream()
                    .filter(t -> Objects.equals(t.get("id"), targetId))
                    .findFirst()
                    .orElse(null);

            if (target == null) continue;

            JournalRecord rec = new JournalRecord();
            rec.setDroneId(droneId);
            rec.setTargetId(targetId);
            rec.setDateTime((String) photo.get("dateTime"));
            rec.setLatitude(toDouble(target.get("latitude")));
            rec.setLongitude(toDouble(target.get("longitude")));
            rec.setAltitude(toDouble(target.get("altitude")));
            rec.setFilePath((String) photo.get("filePath"));

            result.add(rec);
        }

        return result;
    }

    private double toDouble(Object value) {
        if (value instanceof Number num) return num.doubleValue();
        if (value instanceof String s) return Double.parseDouble(s);
        return 0.0;
    }
    
    public static JournalCoreService getInstance() {
        return INSTANCE;
    }
}
