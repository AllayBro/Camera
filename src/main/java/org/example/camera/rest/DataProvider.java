package org.example.camera.rest;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.util.*;

public class DataProvider {

    private final Document doc;

    public DataProvider() throws Exception {
        // Загружаем XML из classpath (resources)
        InputStream input = getClass().getClassLoader().getResourceAsStream("example.xml");
        if (input == null) {
            throw new IllegalStateException("example.xml not found in classpath");
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(input);
    }

    public List<Map<String, String>> getDrones() {
        List<Map<String, String>> list = new ArrayList<>();
        NodeList nodes = doc.getElementsByTagNameNS("*", "Drone");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            Map<String, String> drone = new LinkedHashMap<>();
            drone.put("id", e.getAttribute("id"));
            drone.put("model", e.getAttribute("model"));
            drone.put("status", e.getAttribute("status"));
            list.add(drone);
        }
        return list;
    }

    public List<Map<String, Object>> getTargets() {
        List<Map<String, Object>> list = new ArrayList<>();
        NodeList nodes = doc.getElementsByTagNameNS("*", "Target");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            Element coords = (Element) e.getElementsByTagNameNS("*", "Coordinates").item(0);
            Map<String, Object> target = new LinkedHashMap<>();
            target.put("id", e.getAttribute("id"));
            target.put("name", e.getAttribute("name"));
            target.put("type", e.getAttribute("type"));
            target.put("latitude", Double.parseDouble(coords.getElementsByTagNameNS("*", "Latitude").item(0).getTextContent()));
            target.put("longitude", Double.parseDouble(coords.getElementsByTagNameNS("*", "Longitude").item(0).getTextContent()));
            target.put("altitude", Double.parseDouble(coords.getElementsByTagNameNS("*", "Altitude").item(0).getTextContent()));
            list.add(target);
        }
        return list;
    }

    public List<Map<String, Object>> getPhotos() {
        List<Map<String, Object>> list = new ArrayList<>();
        NodeList nodes = doc.getElementsByTagNameNS("*", "Photo");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            Map<String, Object> photo = new LinkedHashMap<>();
            photo.put("id", e.getAttribute("id"));
            photo.put("droneId", e.getElementsByTagNameNS("*", "DroneID").item(0).getTextContent());
            photo.put("targetId", e.getElementsByTagNameNS("*", "TargetID").item(0).getTextContent());
            photo.put("dateTime", e.getElementsByTagNameNS("*", "DateTime").item(0).getTextContent());
            photo.put("filePath", e.getElementsByTagNameNS("*", "FilePath").item(0).getTextContent());

            Element meta = (Element) e.getElementsByTagNameNS("*", "PhotoMetadata").item(0);
            photo.put("resolution", meta.getElementsByTagNameNS("*", "Resolution").item(0).getTextContent());
            photo.put("fileSize", meta.getElementsByTagNameNS("*", "FileSize").item(0).getTextContent());
            photo.put("format", meta.getElementsByTagNameNS("*", "Format").item(0).getTextContent());
            list.add(photo);
        }
        return list;
    }
}
