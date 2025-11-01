package org.example.camera.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.google.gson.Gson;

import java.util.*;

@RestController
@RequestMapping("/webresources/drone")
public class AllDroneService {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
