package org.example.camera.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.google.gson.Gson;

import java.util.*;

@RestController
@RequestMapping("/webresources/drone")
@Tag(name = "Drones", description = "API для работы с каталогом дронов")
public class AllDroneService {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить каталог дронов",
            description = "Возвращает список всех дронов в формате JSON"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список дронов",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public String getCatalog() {
        try {
            DataProvider xml = new DataProvider();
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("drones", xml.getDrones());
            return new Gson().toJson(data);
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
