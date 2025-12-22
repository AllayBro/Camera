package org.example.camera.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.camera.core.FinesCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.google.gson.*;
import java.util.*;

@RestController
@RequestMapping("/webresources/fines")
@Tag(name = "Fines", description = "API для работы со штрафами")
public class FinesService {

    @Autowired
    private FinesCoreService CORE;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить список всех штрафов",
            description = "Возвращает все зарегистрированные штрафы в формате JSON"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список штрафов",
            content = @Content(schema = @Schema(implementation = String.class))
    )
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
                        .mapToInt(v -> CORE.getPenaltyTable().getOrDefault(v, 0))
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

            // ДОБАВЛЯЕМ штрафы из ядра (бывший SAVED_FINES)
            fines.addAll(CORE.getSavedFines());

            return new Gson().toJson(Map.of("fines", fines));

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Добавить штраф",
            description = "Принимает JSON с данными о штрафе и добавляет его в систему"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Добавленные штрафы",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public String calculateFine(@org.springframework.web.bind.annotation.RequestBody String body) {
        try {
            JsonObject request = JsonParser.parseString(body).getAsJsonObject();
            JsonArray finesArray = request.getAsJsonArray("fines");
            List<Map<String, Object>> added = new ArrayList<>();

            for (JsonElement el : finesArray) {
                JsonObject fine = el.getAsJsonObject();

                // Используем targetId если есть
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
                        .mapToInt(v -> CORE.getPenaltyTable().getOrDefault(v, 0))
                        .sum();

                // сохраняем в ядро (бывший SAVED_FINES)
                CORE.registerFine(droneId, String.join(", ", violations), totalPenalty);

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

                added.add(record);
            }

            return new Gson().toJson(Map.of("fines", added));

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
