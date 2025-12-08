package org.example.camera.client;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import org.example.camera.soap.DroneServiceBean;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Клиентское приложение для веб-сервиса DroneService.
 * Использует статические заглушки (static stubs), сгенерированные из WSDL.
 */
public class DroneServiceClient {

    private static final String WSDL_URL = "http://localhost:8090/camera2/DroneService?wsdl";
    private static final String SERVICE_NAMESPACE = "http://www.example.com/drone/service";
    private static final String SERVICE_NAME = "DroneService";

    public static void main(String[] args) {
        System.out.println("=== Клиент веб-сервиса DroneService ===\n");

        try {
            // Создаем URL для WSDL
            URL wsdlUrl = new URL(WSDL_URL);

            // Создаем сервис из WSDL (как в методичке)
            Service service = Service.create(wsdlUrl, 
                new QName(SERVICE_NAMESPACE, SERVICE_NAME));

            // Получаем прокси-объект с помощью метода get<ServiceName>Port() (как в методичке)
            DroneServicePortType port = service.getPort(
                new QName(SERVICE_NAMESPACE, "DroneServicePort"),
                DroneServicePortType.class);

            // Тест 1: GetDroneInfo - успешный запрос
            System.out.println("Тест 1: GetDroneInfo (успешный запрос)");
            testGetDroneInfo(port, "d1");

            // Тест 2: GetDroneInfo - ошибка (несуществующий дрон)
            System.out.println("\nТест 2: GetDroneInfo (ошибка - несуществующий дрон)");
            testGetDroneInfoError(port, "d999");

            // Тест 3: RegisterFine - успешный запрос
            System.out.println("\nТест 3: RegisterFine (успешный запрос)");
            testRegisterFine(port, "d1", "Превышение скорости", 5000.0);

            // Тест 4: RegisterFine - ошибка валидации
            System.out.println("\nТест 4: RegisterFine (ошибка валидации)");
            testRegisterFineError(port, "", "Нарушение", -100.0);

            // Тест 5: GetFines - успешный запрос
            System.out.println("\nТест 5: GetFines (успешный запрос)");
            testGetFines(port, "d1");

            // Тест 6: GetFines - ошибка (пустой ID)
            System.out.println("\nТест 6: GetFines (ошибка - пустой ID)");
            testGetFinesError(port, "");

        } catch (MalformedURLException e) {
            System.err.println("Ошибка: неправильный URL WSDL: " + e.getMessage());
            e.printStackTrace();
        } catch (WebServiceException e) {
            System.err.println("Ошибка веб-сервиса: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Тест операции GetDroneInfo с успешным результатом.
     */
    private static void testGetDroneInfo(DroneServicePortType port, String droneId) {
        try {
            DroneServiceBean.GetDroneInfoRequest request = new DroneServiceBean.GetDroneInfoRequest();
            request.setDroneId(droneId);

            DroneServiceBean.GetDroneInfoResponse response = port.getDroneInfo(request);
            System.out.println("  Успех! Информация о дроне: " + response.getDroneInfo());
        } catch (Exception e) {
            System.err.println("  Ошибка: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("  Причина: " + e.getCause().getMessage());
            }
        }
    }

    /**
     * Тест операции GetDroneInfo с ошибкой.
     */
    private static void testGetDroneInfoError(DroneServicePortType port, String droneId) {
        try {
            DroneServiceBean.GetDroneInfoRequest request = new DroneServiceBean.GetDroneInfoRequest();
            request.setDroneId(droneId);

            DroneServiceBean.GetDroneInfoResponse response = port.getDroneInfo(request);
            System.out.println("  Неожиданный успех: " + response.getDroneInfo());
        } catch (DroneServiceBean.InvalidDroneIdFaultException e) {
            System.out.println("  Обработана ошибка InvalidDroneIdFault:");
            System.out.println("    Сообщение: " + e.getMessage());
            if (e.getFaultInfo() != null) {
                System.out.println("    ID дрона: " + e.getFaultInfo().getDroneId());
                System.out.println("    Детали: " + e.getFaultInfo().getMessage());
            }
        } catch (Exception e) {
            System.err.println("  Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Тест операции RegisterFine с успешным результатом.
     */
    private static void testRegisterFine(DroneServicePortType port, String droneId, 
                                         String violation, double penalty) {
        try {
            DroneServiceBean.RegisterFineRequest request = new DroneServiceBean.RegisterFineRequest();
            request.setDroneId(droneId);
            request.setViolation(violation);
            request.setPenalty(penalty);

            DroneServiceBean.RegisterFineResponse response = port.registerFine(request);
            System.out.println("  Успех! Результат: " + response.getResult());
        } catch (Exception e) {
            System.err.println("  Ошибка: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("  Причина: " + e.getCause().getMessage());
            }
        }
    }

    /**
     * Тест операции RegisterFine с ошибкой валидации.
     */
    private static void testRegisterFineError(DroneServicePortType port, String droneId, 
                                             String violation, double penalty) {
        try {
            DroneServiceBean.RegisterFineRequest request = new DroneServiceBean.RegisterFineRequest();
            request.setDroneId(droneId);
            request.setViolation(violation);
            request.setPenalty(penalty);

            DroneServiceBean.RegisterFineResponse response = port.registerFine(request);
            System.out.println("  Неожиданный успех: " + response.getResult());
        } catch (DroneServiceBean.ValidationFaultException e) {
            System.out.println("  Обработана ошибка ValidationFault:");
            System.out.println("    Сообщение: " + e.getMessage());
            if (e.getFaultInfo() != null) {
                System.out.println("    Поле: " + e.getFaultInfo().getField());
                System.out.println("    Детали: " + e.getFaultInfo().getMessage());
            }
        } catch (Exception e) {
            System.err.println("  Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Тест операции GetFines с успешным результатом.
     */
    private static void testGetFines(DroneServicePortType port, String droneId) {
        try {
            DroneServiceBean.GetFinesRequest request = new DroneServiceBean.GetFinesRequest();
            request.setDroneId(droneId);

            DroneServiceBean.GetFinesResponse response = port.getFines(request);
            System.out.println("  Успех! Список штрафов:");
            for (String fine : response.getFines()) {
                System.out.println("    - " + fine);
            }
        } catch (Exception e) {
            System.err.println("  Ошибка: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("  Причина: " + e.getCause().getMessage());
            }
        }
    }

    /**
     * Тест операции GetFines с ошибкой.
     */
    private static void testGetFinesError(DroneServicePortType port, String droneId) {
        try {
            DroneServiceBean.GetFinesRequest request = new DroneServiceBean.GetFinesRequest();
            request.setDroneId(droneId);

            DroneServiceBean.GetFinesResponse response = port.getFines(request);
            System.out.println("  Неожиданный успех:");
            for (String fine : response.getFines()) {
                System.out.println("    - " + fine);
            }
        } catch (DroneServiceBean.InvalidDroneIdFaultException e) {
            System.out.println("  Обработана ошибка InvalidDroneIdFault:");
            System.out.println("    Сообщение: " + e.getMessage());
            if (e.getFaultInfo() != null) {
                System.out.println("    ID дрона: " + e.getFaultInfo().getDroneId());
                System.out.println("    Детали: " + e.getFaultInfo().getMessage());
            }
        } catch (Exception e) {
            System.err.println("  Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Интерфейс порта веб-сервиса (SEI - Service Endpoint Interface).
     * Этот интерфейс должен быть сгенерирован из WSDL с помощью wsimport,
     * но для демонстрации создан вручную.
     */
    public interface DroneServicePortType {
        DroneServiceBean.GetDroneInfoResponse getDroneInfo(DroneServiceBean.GetDroneInfoRequest request)
                throws DroneServiceBean.InvalidDroneIdFaultException, DroneServiceBean.ServiceUnavailableFaultException;

        DroneServiceBean.RegisterFineResponse registerFine(DroneServiceBean.RegisterFineRequest request)
                throws DroneServiceBean.ValidationFaultException, DroneServiceBean.ServiceUnavailableFaultException;

        DroneServiceBean.GetFinesResponse getFines(DroneServiceBean.GetFinesRequest request)
                throws DroneServiceBean.InvalidDroneIdFaultException, DroneServiceBean.ServiceUnavailableFaultException;
    }
}

