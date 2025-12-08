package org.example.camera.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.google.gson.*;
import java.util.*;

@RestController
@RequestMapping("/webresources/fines")
public class FinesService {

    private static final Map<String, Integer> PENALTY_TABLE = Map.of(
            "Превышение скорости", 10000,
            "Пересечение двойной сплошной", 15000,
            "Проезд по обочине", 7000,
            "Проезд на красный сигнал", 12000,
            "Остановка в неположенном месте", 5000
    );

    // Общая память для сохранения POST-записей
    private static final List<Map<String, Object>> SAVED_FINES = new ArrayList<>();

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFines() {
        try {
            DataProvider xml = new DataProvider();
            List<Map<String, Object>> photos = xml.getPhotos();
            List<Map<String, Object>> targets = xml.getTargets();
            List<Map<String, Object>> fines = new ArrayList<>();

            for (Map<String, Object> photo : photos) {
                String droneId = (String) photo.get("droneId");
                String targetId = (String) photo.get("targetId");

                Map<String, Object> targetData = targets.stream()
                        .filter(t -> t.get("id").equals(targetId))
                        .findFirst()
                        .orElse(null);
                if (targetData == null) continue;

                List<String> violations =
                        droneId.equals("d1") ? List.of("Превышение скорости") :
                                droneId.equals("d2") ? List.of(
                                        "Превышение скорости",
                                        "Пересечение двойной сплошной",
                                        "Проезд по обочине",
                                        "Проезд на красный сигнал",
                                        "Остановка в неположенном месте") :
                                        List.of();

                int totalPenalty = violations.stream()
                        .mapToInt(v -> PENALTY_TABLE.getOrDefault(v, 0))
                        .sum();

                Map<String, Object> record = new LinkedHashMap<>();
                record.put("id", targetData.get("id"));
                record.put("name", targetData.get("name"));
                record.put("type", targetData.get("type"));
                record.put("latitude", targetData.get("latitude"));
                record.put("longitude", targetData.get("longitude"));
                record.put("altitude", targetData.get("altitude"));
                record.put("droneId", droneId);
                record.put("violations", violations);
                record.put("totalPenalty", totalPenalty);
                fines.add(record);
            }

            // Добавляем записи, созданные через POST
            fines.addAll(SAVED_FINES);

            return new Gson().toJson(Map.of("fines", fines));
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String calculateFine(@RequestBody String body) {
        try {
            JsonObject request = JsonParser.parseString(body).getAsJsonObject();
            JsonArray finesArray = request.getAsJsonArray("fines");
            List<Map<String, Object>> added = new ArrayList<>();

            for (JsonElement el : finesArray) {
                JsonObject fine = el.getAsJsonObject();

                // Используем targetId если есть, иначе id
                String id = fine.has("targetId") ? fine.get("targetId").getAsString() : 
                           fine.has("id") ? fine.get("id").getAsString() : null;
                if (id == null) {
                    throw new IllegalArgumentException("Missing required field: 'id' or 'targetId'");
                }
                
                String name = fine.get("name").getAsString();
                String type = fine.get("type").getAsString();
                double latitude = fine.get("latitude").getAsDouble();
                double longitude = fine.get("longitude").getAsDouble();
                double altitude = fine.get("altitude").getAsDouble();
                String droneId = fine.get("droneId").getAsString();

                List<String> violations = new ArrayList<>();
                if (fine.has("violations")) {
                    fine.getAsJsonArray("violations")
                            .forEach(v -> violations.add(v.getAsString()));
                }

                int totalPenalty = violations.stream()
                        .mapToInt(v -> PENALTY_TABLE.getOrDefault(v, 0))
                        .sum();

                // Формат точно как у GET
                Map<String, Object> record = new LinkedHashMap<>();
                record.put("id", id);
                record.put("name", name);
                record.put("type", type);
                record.put("latitude", latitude);
                record.put("longitude", longitude);
                record.put("altitude", altitude);
                record.put("droneId", droneId);
                record.put("violations", violations);
                record.put("totalPenalty", totalPenalty);

                SAVED_FINES.add(record);
                added.add(record);
            }

            return new Gson().toJson(Map.of("fines", added));
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
