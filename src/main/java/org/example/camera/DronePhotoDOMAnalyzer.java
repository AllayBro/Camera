package org.example.camera;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.*;
import org.xml.sax.SAXParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
public class DronePhotoDOMAnalyzer {

    public static void main(String[] args) {
        InputStream input = null;
        try {
            if (args.length > 0) {
                input = new FileInputStream(args[0]);
            } else {
                input = Thread.currentThread().getContextClassLoader().getResourceAsStream("example.xml");
                if (input == null) {
                    File f = new File("src/main/resources/example.xml");
                    if (f.exists()) input = new FileInputStream(f);
                }
                if (input == null) throw new FileNotFoundException("example.xml not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            InputStream xsdStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("drone_target.xsd");
            if (xsdStream == null) {
                File xf = new File("src/main/resources/drone_target.xsd");
                if (xf.exists()) xsdStream = new FileInputStream(xf);
            }
            if (xsdStream == null) throw new FileNotFoundException("drone_target.xsd not found");
            Schema schema = schemaFactory.newSchema(new StreamSource(xsdStream));
            factory.setSchema(schema);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new CustomErrorHandler());

            Document doc = builder.parse(input);

            generateHtml(doc);

        } catch (SAXParseException e) {
            System.err.println("[VALIDATION ERROR]");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Line: " + e.getLineNumber());
            System.err.println("Column: " + e.getColumnNumber());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (input != null && input != System.in) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void generateHtml(Document doc) {
        try {
            new java.io.File("save").mkdirs();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("save/report_dom.html"), StandardCharsets.UTF_8));

            out.println("<html><head><title>Отчёт о фотографиях (DOM)</title></head><body>");
            out.println("<h1>Данные фотографий</h1>");
            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr><th>Drone ID</th><th>Target ID</th><th>Date/Time</th><th>Latitude</th><th>Longitude</th><th>Altitude</th><th>File Path</th><th>File Size (MB)</th><th>File Size (Bytes)</th></tr>");
            out.println("</thead>");
            out.println("<tbody>");

            NodeList photoList = doc.getElementsByTagNameNS("*", "Photo");

            double totalAltitude = 0.0;
            int photoCount = 0;

            for (int i = 0; i < photoList.getLength(); i++) {
                Element photo = (Element) photoList.item(i);

                String droneID = getTextContent(photo, "DroneID");
                String targetID = getTextContent(photo, "TargetID");
                String dateTime = getTextContent(photo, "DateTime");
                String filePath = getTextContent(photo, "FilePath");

                Element photoMetadata = (Element) photo.getElementsByTagNameNS("*", "PhotoMetadata").item(0);
                String fileSizeStr = getTextContent(photoMetadata, "FileSize");
                double fileSizeMB = parseFileSize(fileSizeStr);
                long fileSizeBytes = (long) (fileSizeMB * 1024 * 1024);

                Element target = findTargetById(doc, targetID);
                double latitude = 0.0, longitude = 0.0, altitude = 0.0;
                if (target != null) {
                    Element coords = (Element) target.getElementsByTagNameNS("*", "Coordinates").item(0);
                    latitude = Double.parseDouble(getTextContent(coords, "Latitude"));
                    longitude = Double.parseDouble(getTextContent(coords, "Longitude"));
                    altitude = Double.parseDouble(getTextContent(coords, "Altitude"));
                }

                totalAltitude += altitude;
                photoCount++;

                out.println("<tr>");
                out.printf("<td>%s</td><td>%s</td><td>%s</td><td>%.4f</td><td>%.4f</td><td>%.2f</td><td>%s</td><td>%.2f</td><td>%d</td>\n",
                        droneID, targetID, dateTime, latitude, longitude, altitude, filePath, fileSizeMB, fileSizeBytes);
                out.println("</tr>");
            }

            out.println("</tbody>");

            double averageAltitude = photoCount > 0 ? totalAltitude / photoCount : 0.0;

            out.println("<tfoot>");
            out.printf("<tr><td colspan='8'><strong>Средняя высота</strong></td><td><strong>%.2f</strong></td></tr>\n", averageAltitude);
            out.println("</tfoot>");

            out.println("</table>");
            out.println("</body></html>");

            out.flush();
            out.close();

            System.out.println("HTML отчёт сохранён в save/report_dom.html");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Element findTargetById(Document doc, String id) {
        NodeList nodeList = doc.getElementsByTagNameNS("*", "Target");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            if (elem.getAttribute("id").equals(id)) {
                return elem;
            }
        }
        return null;
    }

    private static String getTextContent(Element parent, String childName) {
        NodeList nodeList = parent.getElementsByTagNameNS("*", childName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    private static double parseFileSize(String fileSizeStr) {
        try {
            if (fileSizeStr.toUpperCase().endsWith("MB")) {
                return Double.parseDouble(fileSizeStr.replace("MB", "").trim());
            }
        } catch (Exception e) {
        }
        return 0.0;
    }
}



