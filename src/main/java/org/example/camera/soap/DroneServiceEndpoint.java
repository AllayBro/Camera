package org.example.camera.soap;

import org.springframework.ws.server.endpoint.annotation.*;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 Обрабатывает три операции:
 1. GetDroneInfo — возвращает состояние дрона.
 2. RegisterFine — регистрирует штраф.
 3. GetFines — возвращает список штрафов для дрона.
 **/
@Endpoint
public class DroneServiceEndpoint {

    private static final String NAMESPACE = "http://www.example.com/drone/service";
    private static final Map<String, List<String>> FINES = new HashMap<>();

    /**
     * Возвращает информацию о дроне по ID.
     */
    @PayloadRoot(namespace = NAMESPACE, localPart = "GetDroneInfoRequest")
    @ResponsePayload
    public Element getDroneInfo(@RequestPayload Element request) throws InvalidDroneIdException {
        String id = text(request, "droneId");

        if (id == null || id.isBlank()) {
            throw new InvalidDroneIdException("ID дрона не может быть пустым", id);
        }

        String result = switch (id) {
            case "d1" -> "DJI Mavic — активен";
            case "d2" -> "DJI Phantom — неактивен";
            default -> throw new InvalidDroneIdException("Дрон с ID " + id + " не найден", id);
        };

        return response("GetDroneInfoResponse", "info", result);
    }

    /**
     * Регистрирует штраф для дрона.
     */
    @PayloadRoot(namespace = NAMESPACE, localPart = "RegisterFineRequest")
    @ResponsePayload
    public Element registerFine(@RequestPayload Element request) throws ValidationException {
        String id = text(request, "droneId");
        String violation = text(request, "violation");
        String penaltyStr = text(request, "penalty");
        double penalty = penaltyStr.isBlank() ? 0 : Double.parseDouble(penaltyStr);

        if (id.isBlank())
            throw new ValidationException("ID дрона не может быть пустым", "droneId");
        if (violation.isBlank())
            throw new ValidationException("Описание нарушения не может быть пустым", "violation");
        if (penalty < 0)
            throw new ValidationException("Размер штрафа не может быть отрицательным", "penalty");

        FINES.computeIfAbsent(id, k -> new ArrayList<>())
                .add(violation + " (" + penalty + " руб.)");

        return response("RegisterFineResponse", "result", "Штраф зарегистрирован для " + id);
    }

    /**
     * Возвращает все штрафы для указанного дрона.
     */
    @PayloadRoot(namespace = NAMESPACE, localPart = "GetFinesRequest")
    @ResponsePayload
    public Element getFines(@RequestPayload Element request) throws InvalidDroneIdException {
        String id = text(request, "droneId");

        if (id == null || id.isBlank()) {
            throw new InvalidDroneIdException("ID дрона не может быть пустым", id);
        }

        List<String> list = FINES.getOrDefault(id, List.of("Нет штрафов"));
        return response("GetFinesResponse", "fines", String.join(", ", list));
    }

    // ===== Вспомогательные методы =====

    /** Извлекает текст из XML-элемента по имени тега. */
    private static String text(Element parent, String tag) {
        NodeList list = parent.getElementsByTagNameNS("*", tag);
        return list.getLength() > 0 ? list.item(0).getTextContent() : "";
    }

    /** Формирует XML-ответ с указанным корневым и дочерним элементом. */
    private static Element response(String root, String child, String value) {
        try {
            var doc = javax.xml.parsers.DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            var eRoot = doc.createElementNS(NAMESPACE, root);
            var eChild = doc.createElement(child);
            eChild.setTextContent(value);
            eRoot.appendChild(eChild);
            doc.appendChild(eRoot);
            return eRoot;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ===== Классы SOAP-ошибок =====

    @SoapFault(faultCode = FaultCode.CLIENT)
    public static class InvalidDroneIdException extends Exception {
        private final String droneId;

        public InvalidDroneIdException(String message, String id) {
            super(message);
            this.droneId = id;
        }

        public String getDroneId() {
            return droneId;
        }
    }

    @SoapFault(faultCode = FaultCode.CLIENT)
    public static class ValidationException extends Exception {
        private final String field;

        public ValidationException(String message, String field) {
            super(message);
            this.field = field;
        }

        public String getField() {
            return field;
        }
    }
}
