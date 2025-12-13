package org.example.camera.client;

import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.SOAPFaultException;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Клиентское приложение для веб-сервиса DroneService с использованием DII (Dynamic Invocation Interface).
 * DII позволяет вызывать операции веб-сервиса без предварительной генерации классов.
 */
public class DroneServiceDIIClient {

    private static final String WSDL_URL = "http://localhost:8080/camera2/DroneService?wsdl";
    private static final String SERVICE_NAMESPACE = "http://www.example.com/drone/service";
    private static final String SERVICE_NAME = "DroneService";
    private static final String PORT_NAME = "DroneServicePort";

    public static void main(String[] args) {
        System.out.println("=== Клиент веб-сервиса DroneService (DII - Dynamic Invocation Interface) ===\n");

        try {
            // Создаем URL для WSDL
            URL wsdlUrl = new URL(WSDL_URL);

            // Создаем сервис из WSDL
            Service service = Service.create(wsdlUrl, new QName(SERVICE_NAMESPACE, SERVICE_NAME));

            // Создаем Dispatch для динамического вызова
            Dispatch<Source> dispatch = service.createDispatch(
                new QName(SERVICE_NAMESPACE, PORT_NAME),
                Source.class,
                Service.Mode.PAYLOAD
            );

            // Настраиваем привязку
            SOAPBinding binding = (SOAPBinding) dispatch.getBinding();
            binding.setMTOMEnabled(false);

            // Тест 1: GetDroneInfo
            System.out.println("Тест 1: GetDroneInfo");
            testGetDroneInfo(dispatch, "d1");

            // Тест 2: GetDroneInfo - ошибка
            System.out.println("\nТест 2: GetDroneInfo (ошибка - несуществующий дрон)");
            testGetDroneInfoError(dispatch, "d999");

            // Тест 3: RegisterFine
            System.out.println("\nТест 3: RegisterFine");
            testRegisterFine(dispatch, "d1", "Превышение скорости", 5000.0);

            // Тест 4: GetFines
            System.out.println("\nТест 4: GetFines");
            testGetFines(dispatch, "d1");

        } catch (MalformedURLException e) {
            System.err.println("Ошибка: неправильный URL WSDL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Тест операции GetDroneInfo.
     */
    private static void testGetDroneInfo(Dispatch<Source> dispatch, String droneId) {
        try {
            // Создаем SOAP-запрос
            String soapRequest = createGetDroneInfoRequest(droneId);
            Source requestSource = new StreamSource(new StringReader(soapRequest));

            // Вызываем операцию
            Source responseSource = dispatch.invoke(requestSource);

            // Обрабатываем ответ
            String response = sourceToString(responseSource);
            System.out.println("  Успех! Ответ:");
            System.out.println("  " + extractValueFromResponse(response, "droneInfo"));

        } catch (SOAPFaultException e) {
            System.err.println("  SOAP Fault: " + e.getMessage());
            if (e.getFault() != null) {
                System.err.println("    Код ошибки: " + e.getFault().getFaultCode());
                System.err.println("    Сообщение: " + e.getFault().getFaultString());
            }
        } catch (Exception e) {
            System.err.println("  Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Тест операции GetDroneInfo с ошибкой.
     */
    private static void testGetDroneInfoError(Dispatch<Source> dispatch, String droneId) {
        try {
            String soapRequest = createGetDroneInfoRequest(droneId);
            Source requestSource = new StreamSource(new StringReader(soapRequest));

            Source responseSource = dispatch.invoke(requestSource);
            String response = sourceToString(responseSource);
            System.out.println("  Неожиданный успех: " + extractValueFromResponse(response, "droneInfo"));

        } catch (SOAPFaultException e) {
            System.out.println("  Обработана SOAP Fault:");
            System.out.println("    Код ошибки: " + e.getFault().getFaultCode());
            System.out.println("    Сообщение: " + e.getFault().getFaultString());
        } catch (Exception e) {
            System.err.println("  Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Тест операции RegisterFine.
     */
    private static void testRegisterFine(Dispatch<Source> dispatch, String droneId, 
                                         String violation, double penalty) {
        try {
            String soapRequest = createRegisterFineRequest(droneId, violation, penalty);
            Source requestSource = new StreamSource(new StringReader(soapRequest));

            Source responseSource = dispatch.invoke(requestSource);
            String response = sourceToString(responseSource);
            System.out.println("  Успех! Результат: " + extractValueFromResponse(response, "result"));

        } catch (SOAPFaultException e) {
            System.err.println("  SOAP Fault: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("  Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Тест операции GetFines.
     */
    private static void testGetFines(Dispatch<Source> dispatch, String droneId) {
        try {
            String soapRequest = createGetFinesRequest(droneId);
            Source requestSource = new StreamSource(new StringReader(soapRequest));

            Source responseSource = dispatch.invoke(requestSource);
            String response = sourceToString(responseSource);
            System.out.println("  Успех! Список штрафов:");
            System.out.println("  " + extractValueFromResponse(response, "fines"));

        } catch (SOAPFaultException e) {
            System.err.println("  SOAP Fault: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("  Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Создает SOAP-запрос для операции GetDroneInfo.
     */
    private static String createGetDroneInfoRequest(String droneId) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
            " xmlns:ser=\"" + SERVICE_NAMESPACE + "\">" +
            "<soap:Body>" +
            "<ser:GetDroneInfoRequest>" +
            "<ser:droneId>" + droneId + "</ser:droneId>" +
            "</ser:GetDroneInfoRequest>" +
            "</soap:Body>" +
            "</soap:Envelope>";
    }

    /**
     * Создает SOAP-запрос для операции RegisterFine.
     */
    private static String createRegisterFineRequest(String droneId, String violation, double penalty) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
            " xmlns:ser=\"" + SERVICE_NAMESPACE + "\">" +
            "<soap:Body>" +
            "<ser:RegisterFineRequest>" +
            "<ser:droneId>" + droneId + "</ser:droneId>" +
            "<ser:violation>" + violation + "</ser:violation>" +
            "<ser:penalty>" + penalty + "</ser:penalty>" +
            "</ser:RegisterFineRequest>" +
            "</soap:Body>" +
            "</soap:Envelope>";
    }

    /**
     * Создает SOAP-запрос для операции GetFines.
     */
    private static String createGetFinesRequest(String droneId) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
            " xmlns:ser=\"" + SERVICE_NAMESPACE + "\">" +
            "<soap:Body>" +
            "<ser:GetFinesRequest>" +
            "<ser:droneId>" + droneId + "</ser:droneId>" +
            "</ser:GetFinesRequest>" +
            "</soap:Body>" +
            "</soap:Envelope>";
    }

    /**
     * Преобразует Source в строку.
     */
    private static String sourceToString(Source source) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(source, new javax.xml.transform.stream.StreamResult(writer));
        return writer.toString();
    }

    /**
     * Извлекает значение из XML-ответа (упрощенная версия).
     */
    private static String extractValueFromResponse(String xml, String elementName) {
        // В реальном приложении здесь должна быть логика парсинга XML
        // Например, с использованием XPath или DOM
        int startIndex = xml.indexOf("<" + elementName + ">");
        if (startIndex == -1) {
            return "Не найдено";
        }
        startIndex += elementName.length() + 2;
        int endIndex = xml.indexOf("</" + elementName + ">", startIndex);
        if (endIndex == -1) {
            return "Не найдено";
        }
        return xml.substring(startIndex, endIndex);
    }
}

