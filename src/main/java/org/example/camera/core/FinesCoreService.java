package org.example.camera.core;

import java.util.*;

/**
 * Ядро работы со штрафами, без привязки к HTTP / REST / SOAP.
 */
public class FinesCoreService {

    // Таблица базовых штрафов (та самая, что была в REST FinesService)
    private final Map<String, Integer> penaltyTable;

    // Простое in-memory хранилище сохранённых штрафов
    // (сюда можно писать и из REST, и из SOAP)
    private final List<Map<String, Object>> savedFines = new ArrayList<>();

    public FinesCoreService() {
        Map<String, Integer> tmp = new LinkedHashMap<>();
        tmp.put("Превышение скорости", 10000);
        tmp.put("Пересечение двойной сплошной", 15000);
        tmp.put("Проезд по обочине", 7000);
        tmp.put("Проезд на красный сигнал", 12000);
        tmp.put("Остановка в неположенном месте", 5000);
        this.penaltyTable = Collections.unmodifiableMap(tmp);
    }

    /**
     * Таблица базовых штрафов (для расчётов в REST/SOAP).
     */
    public Map<String, Integer> getPenaltyTable() {
        return penaltyTable;
    }

    /**
     * Зарегистрировать штраф для конкретного дрона.
     * Этот метод будем использовать и из REST, и из SOAP.
     */
    public void registerFine(String droneId, String violation, double penalty) {
        if (droneId == null || droneId.isBlank()) {
            throw new IllegalArgumentException("droneId is empty");
        }
        if (violation == null || violation.isBlank()) {
            throw new IllegalArgumentException("violation is empty");
        }

        Map<String, Object> record = new LinkedHashMap<>();
        record.put("droneId", droneId);
        record.put("violation", violation);
        record.put("penalty", penalty);

        savedFines.add(record);
    }

    /**
     * Получить список штрафов по дрону в текстовом виде.
     * Удобно и для REST, и для SOAP.
     */
    public List<String> getFines(String droneId) {
        List<String> result = new ArrayList<>();
        for (Map<String, Object> fine : savedFines) {
            if (Objects.equals(droneId, fine.get("droneId"))) {
                String violation = (String) fine.get("violation");
                Object p = fine.get("penalty");
                double penalty = (p instanceof Number)
                        ? ((Number) p).doubleValue()
                        : 0.0;
                result.add(violation + " (" + penalty + ")");
            }
        }
        return result;
    }

    /**
     * Если нужно где-то отдать "сырые" записи (например, для REST JSON).
     */
    public List<Map<String, Object>> getSavedFines() {
        return Collections.unmodifiableList(savedFines);
    }
}
