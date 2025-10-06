package org.example.camera.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Path("drones")
public class DroneResource {

    private static final String XML_PATH = "C:/Users/AllayBro/Desktop/УЧЕБА/ПО ДЖАВА/Практика/Camera/src/main/resources/example_with_xsd.xml";


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Drone> getAllDrones() {
        List<Drone> drones = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(XML_PATH));

            NodeList droneNodes = doc.getElementsByTagNameNS("http://www.example.com/drone", "Drone");

            for (int i = 0; i < droneNodes.getLength(); i++) {
                Element el = (Element) droneNodes.item(i);
                String id = el.getAttribute("id");
                String model = getChildText(el, "Model");
                String status = getChildText(el, "Status");
                drones.add(new Drone(id, model, status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // JAX-RS автоматически превратит список в JSON
        return drones;
    }

    private String getChildText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagNameNS("http://www.example.com/drone", tagName);
        return list.getLength() > 0 ? list.item(0).getTextContent() : "";
    }
}
