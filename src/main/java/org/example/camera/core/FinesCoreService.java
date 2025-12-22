package org.example.camera.core;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinesCoreService {

    private static final FinesCoreService INSTANCE = new FinesCoreService();
    
    private final Map<String, Integer> penaltyTable;
    private final List<Map<String, Object>> savedFines = new ArrayList<>();

    public FinesCoreService() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("Превышение скорости", 10000);
        m.put("Пересечение двойной сплошной", 15000);
        m.put("Проезд по обочине", 7000);
        m.put("Проезд на красный сигнал", 12000);
        m.put("Остановка в неположенном месте", 5000);
        this.penaltyTable = Collections.unmodifiableMap(m);
    }

    public Map<String, Integer> getPenaltyTable() {
        return penaltyTable;
    }

    public List<Map<String, Object>> getSavedFines() {
        return savedFines;
    }

    // регистрация "полной" записи для REST
    public void registerFineRecord(Map<String, Object> record) {
        savedFines.add(new LinkedHashMap<>(record));
    }

    // упрощённая регистрация для SOAP
    public void registerFine(String droneId, String violation, double penalty) {
        Map<String, Object> record = new LinkedHashMap<>();
        record.put("id", null);
        record.put("name", null);
        record.put("type", null);
        record.put("latitude", null);
        record.put("longitude", null);
        record.put("altitude", null);
        record.put("droneId", droneId);
        record.put("violations", List.of(violation));
        record.put("totalPenalty", (int) Math.round(penalty));
        savedFines.add(record);
    }

    // строки для SOAP-GetFines
    public List<String> getFines(String droneId) {
        List<String> result = new ArrayList<>();

        for (Map<String, Object> record : savedFines) {
            Object recDroneId = record.get("droneId");
            if (!Objects.equals(droneId, recDroneId)) continue;

            @SuppressWarnings("unchecked")
            List<String> violations = (List<String>) record.get("violations");
            Object penaltyObj = record.get("totalPenalty");
            int totalPenalty = penaltyObj instanceof Number
                    ? ((Number) penaltyObj).intValue()
                    : 0;

            String title = (violations == null || violations.isEmpty())
                    ? "Штраф"
                    : String.join(", ", violations);

            result.add(title + " (" + totalPenalty + ")");
        }

        return result;
    }
    
    public static FinesCoreService getInstance() {
        return INSTANCE;
    }
}
