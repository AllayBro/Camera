package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXParseException;

public class DronePhotoStAXAnalyzer {

    public static void main(String[] args) {
        try {
            validateXML();
            parseAndGenerateHTML();

        } catch (SAXParseException e) {
            System.err.println("[VALIDATION ERROR]");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Line: " + e.getLineNumber());
            System.err.println("Column: " + e.getColumnNumber());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void validateXML() throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        
        // Пытаемся загрузить XSD файл из файловой системы
        File xsdFile = new File("src/main/resources/drone_target.xsd");
        if (!xsdFile.exists()) {
            throw new FileNotFoundException("drone_target.xsd not found at: " + xsdFile.getAbsolutePath());
        }
        Schema schema = schemaFactory.newSchema(xsdFile);

        javax.xml.validation.Validator validator = schema.newValidator();
        validator.setErrorHandler(new CustomErrorHandler());

        // Пытаемся загрузить XML файл из файловой системы
        File xmlFile = new File("src/main/resources/example.xml");
        if (!xmlFile.exists()) {
            throw new FileNotFoundException("example.xml not found at: " + xmlFile.getAbsolutePath());
        }
        validator.validate(new StreamSource(xmlFile));
    }

    private static void parseAndGenerateHTML() throws Exception {
        // Пытаемся загрузить XML файл из файловой системы
        File xmlFile = new File("src/main/resources/example.xml");
        if (!xmlFile.exists()) {
            throw new FileNotFoundException("example.xml not found at: " + xmlFile.getAbsolutePath());
        }
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlFile));

        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("save/report_stax.html"), StandardCharsets.UTF_8));

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Отчёт о фотографии дрона (StAX)</title>");
        out.println("<style>");
        out.println("table { width: 100%; border-collapse: collapse; }");
        out.println("th, td { padding: 8px; text-align: center; border: 1px solid black; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("tbody tr:nth-child(odd) { background-color: #f9f9f9; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Данные фотографий</h1>");
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr><th>Drone ID</th><th>Target ID</th><th>Date/Time</th><th>Latitude</th><th>Longitude</th><th>Altitude</th><th>File Path</th><th>File Size (MB)</th><th>File Size (Bytes)</th><th>Altitude × Size</th></tr>");
        out.println("</thead>");
        out.println("<tbody>");

        List<PhotoInfo> photos = new ArrayList<>();
        PhotoInfo currentPhoto = null;
        boolean insidePhoto = false;
        boolean insideTarget = false;
        String currentElement = "";

        double totalAltitude = 0.0;
        double totalAltitudeTimesSize = 0.0;
        int photoCount = 0;

        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLStreamConstants.START_ELEMENT) {
                String localName = reader.getLocalName();
                currentElement = localName;

                if (localName.equals("Photo")) {
                    insidePhoto = true;
                    currentPhoto = new PhotoInfo();
                } else if (localName.equals("Target")) {
                    insideTarget = true;
                }

            } else if (event == XMLStreamConstants.CHARACTERS) {
                String text = reader.getText().trim();
                if (text.isEmpty()) continue;

                if (insidePhoto && currentPhoto != null) {
                    switch (currentElement) {
                        case "DroneID":
                            currentPhoto.droneID = text;
                            break;
                        case "TargetID":
                            currentPhoto.targetID = text;
                            break;
                        case "DateTime":
                            currentPhoto.dateTime = text;
                            break;
                        case "FilePath":
                            currentPhoto.filePath = text;
                            break;
                        case "FileSize":
                            currentPhoto.fileSizeMB = parseFileSize(text);
                            break;
                    }
                }
                if (insideTarget && currentPhoto != null) {
                    switch (currentElement) {
                        case "Latitude":
                            currentPhoto.latitude = Double.parseDouble(text);
                            break;
                        case "Longitude":
                            currentPhoto.longitude = Double.parseDouble(text);
                            break;
                        case "Altitude":
                            currentPhoto.altitude = Double.parseDouble(text);
                            break;
                    }
                }

            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String localName = reader.getLocalName();
                if (localName.equals("Photo")) {
                    insidePhoto = false;
                    photos.add(currentPhoto);
                    currentPhoto = null;
                }
                if (localName.equals("Target")) {
                    insideTarget = false;
                }
            }
        }

        new File("save").mkdirs();

        for (PhotoInfo photo : photos) {
            double fileSizeBytes = photo.fileSizeMB * 1024 * 1024;
            double altitudeTimesSize = photo.altitude * photo.fileSizeMB;

            out.println("<tr>");
            out.printf("<td>%s</td><td>%s</td><td>%s</td><td>%.4f</td><td>%.4f</td><td>%.2f</td><td>%s</td><td>%.2f</td><td>%.0f</td><td>%.2f</td>\n",
                    photo.droneID, photo.targetID, photo.dateTime, photo.latitude, photo.longitude, photo.altitude, photo.filePath, photo.fileSizeMB, fileSizeBytes, altitudeTimesSize);
            out.println("</tr>");

            totalAltitude += photo.altitude;
            totalAltitudeTimesSize += altitudeTimesSize;
            photoCount++;
        }

        out.println("</tbody>");

        double averageAltitude = photoCount > 0 ? totalAltitude / photoCount : 0.0;

        out.println("<tfoot>");
        out.printf("<tr><td colspan='5'><strong>Средняя высота</strong></td><td><strong>%.2f</strong></td><td colspan='4'></td></tr>\n", averageAltitude);
        out.printf("<tr><td colspan='5'><strong>Сумма (Altitude × Size)</strong></td><td colspan='5'><strong>%.2f</strong></td></tr>\n", totalAltitudeTimesSize);
        out.println("</tfoot>");

        out.println("</table>");
        out.println("</body></html>");

        out.flush();
        out.close();

        System.out.println("HTML отчёт сохранён в save/report_stax.html");
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

    private static class PhotoInfo {
        String droneID;
        String targetID;
        String dateTime;
        String filePath;
        double latitude;
        double longitude;
        double altitude;
        double fileSizeMB;
    }
}


