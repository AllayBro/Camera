package org.example.camera.soap;

import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.WebServiceContext;
import jakarta.annotation.Resource;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.*;

/**
 * Сессионный компонент без состояния с представлением в виде веб-сервиса.
 * Реализует три операции:
 * 1. GetDroneInfo — возвращает состояние дрона.
 * 2. RegisterFine — регистрирует штраф.
 * 3. GetFines — возвращает список штрафов для дрона.
 */
@Stateless
@WebService(
    name = "DroneServicePortType",
    targetNamespace = "http://www.example.com/drone/service",
    serviceName = "DroneService",
    portName = "DroneServicePort",
    wsdlLocation = "wsdl/DroneServiceConcrete.wsdl"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = jakarta.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class DroneServiceBean {

    @Resource
    WebServiceContext context;

    private static final String NAMESPACE = "http://www.example.com/drone/service";
    private static final Map<String, List<String>> FINES = new HashMap<>();

    /**
     * Возвращает информацию о дроне по ID.
     */
    @WebMethod(operationName = "GetDroneInfo")
    @WebResult(name = "GetDroneInfoResponse", targetNamespace = NAMESPACE)
    public GetDroneInfoResponse getDroneInfo(
            @WebParam(name = "GetDroneInfoRequest", targetNamespace = NAMESPACE) GetDroneInfoRequest request)
            throws InvalidDroneIdFaultException, ServiceUnavailableFaultException {
        
        String id = request.getDroneId();

        if (id == null || id.isBlank()) {
            InvalidDroneIdFault fault = new InvalidDroneIdFault();
            fault.setMessage("ID дрона не может быть пустым");
            fault.setDroneId(id);
            throw new InvalidDroneIdFaultException("ID дрона не может быть пустым", fault);
        }

        String result = switch (id) {
            case "d1" -> "DJI Mavic — активен";
            case "d2" -> "DJI Phantom — неактивен";
            default -> {
                InvalidDroneIdFault fault = new InvalidDroneIdFault();
                fault.setMessage("Дрон с ID " + id + " не найден");
                fault.setDroneId(id);
                throw new InvalidDroneIdFaultException("Дрон с ID " + id + " не найден", fault);
            }
        };

        GetDroneInfoResponse response = new GetDroneInfoResponse();
        response.setDroneInfo(result);
        return response;
    }

    /**
     * Регистрирует штраф для дрона.
     */
    @WebMethod(operationName = "RegisterFine")
    @WebResult(name = "RegisterFineResponse", targetNamespace = NAMESPACE)
    public RegisterFineResponse registerFine(
            @WebParam(name = "RegisterFineRequest", targetNamespace = NAMESPACE) RegisterFineRequest request)
            throws ValidationFaultException, ServiceUnavailableFaultException {
        
        String id = request.getDroneId();
        String violation = request.getViolation();
        double penalty = request.getPenalty();

        if (id == null || id.isBlank()) {
            ValidationFault fault = new ValidationFault();
            fault.setMessage("ID дрона не может быть пустым");
            fault.setField("droneId");
            throw new ValidationFaultException("ID дрона не может быть пустым", fault);
        }
        if (violation == null || violation.isBlank()) {
            ValidationFault fault = new ValidationFault();
            fault.setMessage("Описание нарушения не может быть пустым");
            fault.setField("violation");
            throw new ValidationFaultException("Описание нарушения не может быть пустым", fault);
        }
        if (penalty < 0) {
            ValidationFault fault = new ValidationFault();
            fault.setMessage("Размер штрафа не может быть отрицательным");
            fault.setField("penalty");
            throw new ValidationFaultException("Размер штрафа не может быть отрицательным", fault);
        }

        FINES.computeIfAbsent(id, k -> new ArrayList<>())
                .add(violation + " (" + penalty + " руб.)");

        RegisterFineResponse response = new RegisterFineResponse();
        response.setResult("Штраф зарегистрирован для " + id);
        return response;
    }

    /**
     * Возвращает все штрафы для указанного дрона.
     */
    @WebMethod(operationName = "GetFines")
    @WebResult(name = "GetFinesResponse", targetNamespace = NAMESPACE)
    public GetFinesResponse getFines(
            @WebParam(name = "GetFinesRequest", targetNamespace = NAMESPACE) GetFinesRequest request)
            throws InvalidDroneIdFaultException, ServiceUnavailableFaultException {
        
        String id = request.getDroneId();

        if (id == null || id.isBlank()) {
            InvalidDroneIdFault fault = new InvalidDroneIdFault();
            fault.setMessage("ID дрона не может быть пустым");
            fault.setDroneId(id);
            throw new InvalidDroneIdFaultException("ID дрона не может быть пустым", fault);
        }

        List<String> list = FINES.getOrDefault(id, List.of("Нет штрафов"));
        GetFinesResponse response = new GetFinesResponse();
        response.getFines().addAll(list);
        return response;
    }

    // ===== Классы данных для запросов и ответов =====

    @XmlRootElement(name = "GetDroneInfoRequest", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "GetDroneInfoRequest", namespace = NAMESPACE, propOrder = {"droneId"})
    public static class GetDroneInfoRequest {
        @XmlElement(required = true)
        private String droneId;

        public String getDroneId() {
            return droneId;
        }

        public void setDroneId(String droneId) {
            this.droneId = droneId;
        }
    }

    @XmlRootElement(name = "GetDroneInfoResponse", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "GetDroneInfoResponse", namespace = NAMESPACE, propOrder = {"droneInfo"})
    public static class GetDroneInfoResponse {
        @XmlElement(required = true)
        private String droneInfo;

        public String getDroneInfo() {
            return droneInfo;
        }

        public void setDroneInfo(String droneInfo) {
            this.droneInfo = droneInfo;
        }
    }

    @XmlRootElement(name = "RegisterFineRequest", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RegisterFineRequest", namespace = NAMESPACE, propOrder = {"droneId", "violation", "penalty"})
    public static class RegisterFineRequest {
        @XmlElement(required = true)
        private String droneId;
        @XmlElement(required = true)
        private String violation;
        @XmlElement(required = true)
        private double penalty;

        public String getDroneId() {
            return droneId;
        }

        public void setDroneId(String droneId) {
            this.droneId = droneId;
        }

        public String getViolation() {
            return violation;
        }

        public void setViolation(String violation) {
            this.violation = violation;
        }

        public double getPenalty() {
            return penalty;
        }

        public void setPenalty(double penalty) {
            this.penalty = penalty;
        }
    }

    @XmlRootElement(name = "RegisterFineResponse", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "RegisterFineResponse", namespace = NAMESPACE, propOrder = {"result"})
    public static class RegisterFineResponse {
        @XmlElement(required = true)
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    @XmlRootElement(name = "GetFinesRequest", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "GetFinesRequest", namespace = NAMESPACE, propOrder = {"droneId"})
    public static class GetFinesRequest {
        @XmlElement(required = true)
        private String droneId;

        public String getDroneId() {
            return droneId;
        }

        public void setDroneId(String droneId) {
            this.droneId = droneId;
        }
    }

    @XmlRootElement(name = "GetFinesResponse", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "GetFinesResponse", namespace = NAMESPACE, propOrder = {"fines"})
    public static class GetFinesResponse {
        @XmlElement(required = true)
        private List<String> fines = new ArrayList<>();

        public List<String> getFines() {
            return fines;
        }

        public void setFines(List<String> fines) {
            this.fines = fines;
        }
    }

    // ===== Классы для SOAP-ошибок =====

    @XmlRootElement(name = "InvalidDroneIdFault", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "InvalidDroneIdFault", namespace = NAMESPACE, propOrder = {"message", "droneId"})
    public static class InvalidDroneIdFault {
        @XmlElement(required = true)
        private String message;
        @XmlElement(required = true)
        private String droneId;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDroneId() {
            return droneId;
        }

        public void setDroneId(String droneId) {
            this.droneId = droneId;
        }
    }

    @XmlRootElement(name = "ServiceUnavailableFault", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "ServiceUnavailableFault", namespace = NAMESPACE, propOrder = {"message", "errorCode"})
    public static class ServiceUnavailableFault {
        @XmlElement(required = true)
        private String message;
        @XmlElement(required = true)
        private String errorCode;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
    }

    @XmlRootElement(name = "ValidationFault", namespace = NAMESPACE)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "ValidationFault", namespace = NAMESPACE, propOrder = {"message", "field"})
    public static class ValidationFault {
        @XmlElement(required = true)
        private String message;
        @XmlElement(required = true)
        private String field;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

    // ===== Исключения для SOAP-ошибок =====

    @jakarta.xml.ws.WebFault(name = "InvalidDroneIdFault", targetNamespace = NAMESPACE)
    public static class InvalidDroneIdFaultException extends Exception {
        private InvalidDroneIdFault faultInfo;

        public InvalidDroneIdFaultException(String message, InvalidDroneIdFault faultInfo) {
            super(message);
            this.faultInfo = faultInfo;
        }

        public InvalidDroneIdFaultException(String message, InvalidDroneIdFault faultInfo, Throwable cause) {
            super(message, cause);
            this.faultInfo = faultInfo;
        }

        public InvalidDroneIdFault getFaultInfo() {
            return faultInfo;
        }
    }

    @jakarta.xml.ws.WebFault(name = "ServiceUnavailableFault", targetNamespace = NAMESPACE)
    public static class ServiceUnavailableFaultException extends Exception {
        private ServiceUnavailableFault faultInfo;

        public ServiceUnavailableFaultException(String message, ServiceUnavailableFault faultInfo) {
            super(message);
            this.faultInfo = faultInfo;
        }

        public ServiceUnavailableFaultException(String message, ServiceUnavailableFault faultInfo, Throwable cause) {
            super(message, cause);
            this.faultInfo = faultInfo;
        }

        public ServiceUnavailableFault getFaultInfo() {
            return faultInfo;
        }
    }

    @jakarta.xml.ws.WebFault(name = "ValidationFault", targetNamespace = NAMESPACE)
    public static class ValidationFaultException extends Exception {
        private ValidationFault faultInfo;

        public ValidationFaultException(String message, ValidationFault faultInfo) {
            super(message);
            this.faultInfo = faultInfo;
        }

        public ValidationFaultException(String message, ValidationFault faultInfo, Throwable cause) {
            super(message, cause);
            this.faultInfo = faultInfo;
        }

        public ValidationFault getFaultInfo() {
            return faultInfo;
        }
    }
}

