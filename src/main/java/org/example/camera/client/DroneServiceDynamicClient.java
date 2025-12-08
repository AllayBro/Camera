package org.example.camera.client;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;

import javax.xml.namespace.QName;
import java.net.URL;

public class DroneServiceDynamicClient {

    private static final String WSDL_URL =
            "http://localhost:8080/camera2/DroneService?wsdl";

    private static final String NS =
            "http://www.example.com/drone/service";

    private static final String SERVICE_NAME = "DroneService";
    private static final String PORT_NAME = "DroneServicePort";

    public static void main(String[] args) throws Exception {

        System.out.println("=== Динамический SOAP-клиент DroneService ===\n");

        URL wsdlURL = new URL(WSDL_URL);

        QName serviceQName = new QName(NS, SERVICE_NAME);
        QName portQName = new QName(NS, PORT_NAME);

        // Создаём Service
        Service service = Service.create(wsdlURL, serviceQName);

        // Создаём Dispatch — DYNAMIC CLIENT
        Dispatch<SOAPMessage> dispatch = service.createDispatch(
                portQName,
                SOAPMessage.class,
                Service.Mode.MESSAGE
        );
        System.out.println("Тест 1: GetDroneInfo");

        SOAPMessage req1 = buildGetDroneInfoRequest("d1");
        SOAPMessage resp1 = dispatch.invoke(req1);
        printResponse(resp1);
        System.out.println("\nТест 2: RegisterFine");

        SOAPMessage req2 = buildRegisterFineRequest("d1", "Нарушение", 3000.0);
        SOAPMessage resp2 = dispatch.invoke(req2);
        printResponse(resp2);
        System.out.println("\nТест 3: GetFines");

        SOAPMessage req3 = buildGetFinesRequest("d1");
        SOAPMessage resp3 = dispatch.invoke(req3);
        printResponse(resp3);
    }
    private static SOAPMessage buildGetDroneInfoRequest(String droneId) throws Exception {
        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage msg = mf.createMessage();

        SOAPBody body = msg.getSOAPBody();
        SOAPElement root = body.addChildElement("GetDroneInfoRequest", "", NS);
        root.addChildElement("droneId").addTextNode(droneId);

        msg.saveChanges();
        return msg;
    }

    private static SOAPMessage buildRegisterFineRequest(String droneId, String violation, double penalty)
            throws Exception {

        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage msg = mf.createMessage();

        SOAPBody body = msg.getSOAPBody();
        SOAPElement root = body.addChildElement("RegisterFineRequest", "", NS);

        root.addChildElement("droneId").addTextNode(droneId);
        root.addChildElement("violation").addTextNode(violation);
        root.addChildElement("penalty").addTextNode(String.valueOf(penalty));

        msg.saveChanges();
        return msg;
    }


    private static SOAPMessage buildGetFinesRequest(String droneId) throws Exception {
        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage msg = mf.createMessage();

        SOAPBody body = msg.getSOAPBody();
        SOAPElement root = body.addChildElement("GetFinesRequest", "", NS);
        root.addChildElement("droneId").addTextNode(droneId);

        msg.saveChanges();
        return msg;
    }
    private static void printResponse(SOAPMessage response) throws Exception {
        System.out.println("Ответ SOAP:");
        response.writeTo(System.out);
        System.out.println("\n");
    }
}
