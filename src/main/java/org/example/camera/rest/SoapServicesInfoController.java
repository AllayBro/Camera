package org.example.camera.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/webresources/soap-services")
@Tag(name = "SOAP Services", description = "Информация о SOAP веб-сервисах и их WSDL")
public class SoapServicesInfoController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить информацию о всех SOAP сервисах",
            description = "Возвращает список всех доступных SOAP веб-сервисов с их endpoints и WSDL URL"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список SOAP сервисов",
            content = @Content(schema = @Schema(implementation = Map.class))
    )
    public Map<String, Object> getSoapServicesInfo() {
        Map<String, Object> response = new LinkedHashMap<>();
        
        // DroneService
        Map<String, Object> droneService = new LinkedHashMap<>();
        droneService.put("name", "DroneService");
        droneService.put("endpoint", "http://localhost:8090/camera2/DroneService");
        droneService.put("wsdl", "http://localhost:8090/camera2/DroneService?wsdl");
        droneService.put("staticWsdl", "http://localhost:8090/camera2/ws/droneService.wsdl");
        droneService.put("abstractWsdl", "http://localhost:8090/camera2/ws/droneServiceAbstract.wsdl");
        droneService.put("description", "SOAP веб-сервис для работы с дронами");
        Map<String, String> droneOperations = new LinkedHashMap<>();
        droneOperations.put("GetDroneInfo", "Получение информации о дроне по ID");
        droneOperations.put("RegisterFine", "Регистрация штрафа для дрона");
        droneOperations.put("GetFines", "Получение списка штрафов для дрона");
        droneService.put("operations", droneOperations);
        
        // FinesService
        Map<String, Object> finesService = new LinkedHashMap<>();
        finesService.put("name", "FinesService");
        finesService.put("endpoint", "http://localhost:8090/camera2/FinesService");
        finesService.put("wsdl", "http://localhost:8090/camera2/FinesService?wsdl");
        finesService.put("description", "SOAP веб-сервис для работы со штрафами");
        Map<String, String> finesOperations = new LinkedHashMap<>();
        finesOperations.put("RegisterFine", "Регистрация штрафа");
        finesOperations.put("GetFines", "Получение списка штрафов для дрона");
        finesService.put("operations", finesOperations);
        
        // JournalService
        Map<String, Object> journalService = new LinkedHashMap<>();
        journalService.put("name", "JournalService");
        journalService.put("endpoint", "http://localhost:8090/camera2/JournalService");
        journalService.put("wsdl", "http://localhost:8090/camera2/JournalService?wsdl");
        journalService.put("description", "SOAP веб-сервис журнала дронов");
        Map<String, String> journalOperations = new LinkedHashMap<>();
        journalOperations.put("GetDroneJournal", "Получение журнала записей для дрона");
        journalService.put("operations", journalOperations);
        
        // BPEL Process WSDL
        Map<String, Object> bpelWsdl = new LinkedHashMap<>();
        bpelWsdl.put("name", "DroneFineProcess (BPEL)");
        bpelWsdl.put("wsdl", "http://localhost:8090/camera2/services/DroneFineProcess?wsdl");
        bpelWsdl.put("endpoint", "http://localhost:8090/camera2/services/DroneFineProcess");
        bpelWsdl.put("description", "WSDL описание BPEL процесса обработки штрафов");
        
        response.put("soapServices", Map.of(
                "droneService", droneService,
                "finesService", finesService,
                "journalService", journalService
        ));
        response.put("bpelProcess", bpelWsdl);
        response.put("note", "Для тестирования SOAP сервисов используйте SoapUI, Postman, Bruno или другой SOAP клиент. Импортируйте WSDL по указанным URL.");
        
        return response;
    }
}

