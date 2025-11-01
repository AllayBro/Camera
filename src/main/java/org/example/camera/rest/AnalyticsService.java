package org.example.camera.rest;

import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.springframework.http.MediaType;

import java.util.*;

@RestController
@RequestMapping("/webresources/analytics")
public class AnalyticsService {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
