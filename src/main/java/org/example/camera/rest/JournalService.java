package org.example.camera.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.camera.core.JournalCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.google.gson.*;
import java.util.*;

@RestController
@RequestMapping("/webresources/journal")
@Tag(name = "Journal", description = "API для работы с журналом дронов")
public class JournalService {

    @Autowired
    private JournalCoreService CORE;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить журнал дронов",
            description = "Возвращает журнал записей для всех дронов в формате JSON"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Журнал записей",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public String getJournal() {
        try {
            // Получаем журнал для всех дронов
            // Для простоты получаем журнал для известных дронов
            List<String> droneIds = List.of("d1", "d2");
            List<Map<String, Object>> allRecords = new ArrayList<>();

            for (String droneId : droneIds) {
                var records = CORE.getDroneJournal(droneId);
                for (var record : records) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("droneId", record.getDroneId());
                    map.put("targetId", record.getTargetId());
                    map.put("dateTime", record.getDateTime());
                    map.put("latitude", record.getLatitude());
                    map.put("longitude", record.getLongitude());
                    map.put("altitude", record.getAltitude());
                    map.put("filePath", record.getFilePath());
                    allRecords.add(map);
                }
            }

            return new Gson().toJson(Map.of("journal", allRecords));

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}

