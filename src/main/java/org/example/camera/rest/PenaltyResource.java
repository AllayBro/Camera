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


@Path("penalties")
public class PenaltyResource {

    private static final String XML_PATH = "C:/Users/AllayBro/Desktop/УЧЕБА/ПО ДЖАВА/Практика/Camera/src/main/resources/example_with_xsd.xml";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Penalty> getAllPenalties() {
        List<Penalty> penalties = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(XML_PATH));

            NodeList photoNodes = doc.getElementsByTagNameNS("http://www.example.com/drone", "Photo");

            for (int i = 0; i < photoNodes.getLength(); i++) {
                Element el = (Element) photoNodes.item(i);

                String id = el.getAttribute("id");
                String droneId = getChildText(el, "DroneID");
                String targetId = getChildText(el, "TargetID");
                String dateTime = getChildText(el, "DateTime");
                String filePath = getChildText(el, "FilePath");

                Element metaEl = (Element) el.getElementsByTagNameNS("http://www.example.com/drone", "PhotoMetadata").item(0);
                String resolution = getChildText(metaEl, "Resolution");
                String fileSize = getChildText(metaEl, "FileSize");
                String format = getChildText(metaEl, "Format");

                PhotoMetadata meta = new PhotoMetadata(resolution, fileSize, format);
                penalties.add(new Penalty(id, droneId, targetId, dateTime, filePath, meta));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return penalties; // сериализация в JSON выполняется JAX-RS
    }

    private String getChildText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagNameNS("http://www.example.com/drone", tagName);
        return list.getLength() > 0 ? list.item(0).getTextContent() : "";
    }
}
