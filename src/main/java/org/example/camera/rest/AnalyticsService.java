package org.example.camera.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.springframework.http.MediaType;

import java.util.*;

@RestController
@RequestMapping("/webresources/analytics")
@Tag(name = "Analytics", description = "API для получения аналитики по фотографиям дронов")
public class AnalyticsService {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить аналитику",
            description = "Возвращает статистику по фотографиям и среднюю высоту в формате JSON"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Аналитика по фотографиям",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public String getAnalytics() {
        try {
            DataProvider xml = new DataProvider();
            List<Map<String,Object>> photos = xml.getPhotos();
            double avgAlt = xml.getTargets().stream()
                    .mapToDouble(t -> (double) t.get("altitude")).average().orElse(0);
            Map<String,Object> res = new LinkedHashMap<>();
            res.put("photos", photos);
            res.put("average_altitude", avgAlt);
            return new Gson().toJson(res);
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
