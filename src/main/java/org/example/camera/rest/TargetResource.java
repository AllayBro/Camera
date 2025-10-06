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

@Path("targets")
public class TargetResource {

    private static final String XML_PATH = "C:/Users/AllayBro/Desktop/УЧЕБА/ПО ДЖАВА/Практика/Camera/src/main/resources/example_with_xsd.xml";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Target> getAllTargets() {
        List<Target> targets = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(XML_PATH));

            NodeList targetNodes = doc.getElementsByTagNameNS("http://www.example.com/drone", "Target");

            for (int i = 0; i < targetNodes.getLength(); i++) {
                Element el = (Element) targetNodes.item(i);

                String id = el.getAttribute("id");
                String name = getChildText(el, "Name");
                String type = getChildText(el, "Type");

                Element coordsEl = (Element) el.getElementsByTagNameNS("http://www.example.com/drone", "Coordinates").item(0);
                double lat = Double.parseDouble(getChildText(coordsEl, "Latitude"));
                double lon = Double.parseDouble(getChildText(coordsEl, "Longitude"));
                double alt = Double.parseDouble(getChildText(coordsEl, "Altitude"));
                String precision = coordsEl.getAttribute("precision");

                Coordinates coords = new Coordinates(lat, lon, alt, precision);
                targets.add(new Target(id, name, type, coords));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return targets; // JAX-RS сам преобразует список в JSON
    }

    private String getChildText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagNameNS("http://www.example.com/drone", tagName);
        return list.getLength() > 0 ? list.item(0).getTextContent() : "";
    }
}
