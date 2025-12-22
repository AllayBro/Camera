package org.example.camera.soap;

import jakarta.ejb.Stateless;
import jakarta.jws.*;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.BindingType;

import org.example.camera.core.FinesCoreService;
import org.example.camera.soap.fines.dto.*;

import java.util.List;

@Stateless
@WebService(
        name = "FinesServicePortType",
        targetNamespace = "http://www.example.com/fines/service",
        serviceName = "FinesService",
        portName = "FinesServicePort"
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = jakarta.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class FinesServiceBean {

    private final FinesCoreService core = FinesCoreService.getInstance();

    @WebMethod(operationName = "RegisterFine")
    @WebResult(name = "RegisterFineResponse", targetNamespace = "http://www.example.com/fines/service")
    public RegisterFineResponse registerFine(
            @WebParam(name = "RegisterFineRequest", targetNamespace = "http://www.example.com/fines/service")
            RegisterFineRequest request
    ) {
        core.registerFine(
                request.getDroneId(),
                request.getViolation(),
                request.getPenalty()
        );

        RegisterFineResponse resp = new RegisterFineResponse();
        resp.setResult("OK");
        return resp;
    }

    @WebMethod(operationName = "GetFines")
    @WebResult(name = "GetFinesResponse", targetNamespace = "http://www.example.com/fines/service")
    public GetFinesResponse getFines(
            @WebParam(name = "GetFinesRequest", targetNamespace = "http://www.example.com/fines/service")
            GetFinesRequest request
    ) {
        List<String> fines = core.getFines(request.getDroneId());

        GetFinesResponse resp = new GetFinesResponse();
        resp.setFines(fines);
        return resp;
    }
}
