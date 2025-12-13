package org.example.camera.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Camera12 API Documentation")
                        .version("1.0.0")
                        .description("""
                                ## REST API
                                RESTful API для работы с дронами, штрафами и аналитикой.
                                
                                ## SOAP Web Services
                                
                                ### DroneService
                                - **Endpoint:** `http://localhost:8090/camera2/DroneService`
                                - **WSDL:** `http://localhost:8090/camera2/DroneService?wsdl`
                                - **Статический WSDL:** `http://localhost:8090/camera2/ws/droneService.wsdl`
                                - **Операции:**
                                  - `GetDroneInfo` - получение информации о дроне
                                  - `RegisterFine` - регистрация штрафа
                                  - `GetFines` - получение списка штрафов
                                
                                ### FinesService
                                - **Endpoint:** `http://localhost:8090/camera2/FinesService`
                                - **WSDL:** `http://localhost:8090/camera2/FinesService?wsdl`
                                - **Операции:**
                                  - `RegisterFine` - регистрация штрафа
                                  - `GetFines` - получение списка штрафов для дрона
                                
                                ### JournalService
                                - **Endpoint:** `http://localhost:8090/camera2/JournalService`
                                - **WSDL:** `http://localhost:8090/camera2/JournalService?wsdl`
                                - **Операции:**
                                  - `GetDroneJournal` - получение журнала записей для дрона
                                
                                ## WSDL Files
                                - **BPEL Process:** `http://localhost:8090/camera2/services/DroneFineProcess?wsdl`
                                - **DroneService Abstract:** `http://localhost:8090/camera2/ws/droneServiceAbstract.wsdl`
                                
                                **Примечание:** SOAP сервисы используют WSDL для описания. Для тестирования SOAP используйте SoapUI, Postman или Bruno с импортом WSDL.
                                """)
                        .contact(new Contact()
                                .name("Camera12 Project")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8090/camera2")
                                .description("Development Server")
                ));
    }
}

