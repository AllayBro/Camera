package org.example.camera.soap;

import jakarta.jws.*;
import jakarta.ejb.Stateless;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import org.example.camera.core.JournalCoreService;
import org.example.camera.soap.journal.dto.*;

import java.util.List;

@Stateless
@WebService(
        name = "JournalServicePortType",
        targetNamespace = "http://www.example.com/journal/service",
        serviceName = "JournalService",
        portName = "JournalServicePort"
)
@BindingType(SOAPBinding.SOAP11HTTP_BINDING)
public class JournalServiceBean {

    private final JournalCoreService core = new JournalCoreService();

    @WebMethod(operationName = "GetDroneJournal")
    @WebResult(name = "GetDroneJournalResponse", targetNamespace = "http://www.example.com/journal/service")
    public GetDroneJournalResponse getDroneJournal(
            @WebParam(name = "GetDroneJournalRequest", targetNamespace = "http://www.example.com/journal/service")
            GetDroneJournalRequest request
    ) {
        List<JournalRecord> list = core.getDroneJournal(request.getDroneId());

        GetDroneJournalResponse response = new GetDroneJournalResponse();
        response.setRecords(list);
        return response;
    }
}
