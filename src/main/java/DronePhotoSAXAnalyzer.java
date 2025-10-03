package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class DronePhotoSAXAnalyzer {

    public static void main(String[] args) {
        try {
            InputStream input;
            if (args.length > 0) {
                input = new FileInputStream(args[0]);
            } else {
                // Используем файл по умолчанию из файловой системы
                File defaultFile = new File("src/main/resources/example.xml");
                if (defaultFile.exists()) {
                    input = new FileInputStream(defaultFile);
                    System.out.println("Используется файл по умолчанию: " + defaultFile.getAbsolutePath());
                } else {
                    throw new FileNotFoundException("example.xml not found at: " + defaultFile.getAbsolutePath());
                }
            }

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Используем файл XSD из файловой системы
            File xsdFile = new File("src/main/resources/drone_target.xsd");
            if (!xsdFile.exists()) {
                throw new FileNotFoundException("drone_target.xsd not found at: " + xsdFile.getAbsolutePath());
            }
            Schema schema = schemaFactory.newSchema(xsdFile);
            factory.setSchema(schema);

            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new CustomErrorHandler());

            DroneHandler handler = new DroneHandler();
            reader.setContentHandler(handler);

            reader.parse(new InputSource(input));

            handler.generateHtml();

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
}

// ====== SAX обработчик ======

class DroneHandler extends DefaultHandler {

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

    private List<PhotoInfo> photos = new ArrayList<>();
    private PhotoInfo currentPhoto;
    private StringBuilder characters = new StringBuilder();
    private boolean inPhoto = false;
    private boolean inTarget = false;

    private double currentLatitude, currentLongitude, currentAltitude;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        characters.setLength(0);

        if (localName.equals("Photo")) {
            inPhoto = true;
            currentPhoto = new PhotoInfo();
        }
        if (localName.equals("Target")) {
            inTarget = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String text = characters.toString().trim();

        if (inPhoto && currentPhoto != null) {
            switch (localName) {
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
                case "Photo":
                    currentPhoto.latitude = currentLatitude;
                    currentPhoto.longitude = currentLongitude;
                    currentPhoto.altitude = currentAltitude;
                    photos.add(currentPhoto);
                    inPhoto = false;
                    break;
            }
        }

        if (inTarget) {
            switch (localName) {
                case "Latitude":
                    currentLatitude = Double.parseDouble(text);
                    break;
                case "Longitude":
                    currentLongitude = Double.parseDouble(text);
                    break;
                case "Altitude":
                    currentAltitude = Double.parseDouble(text);
                    break;
                case "Target":
                    inTarget = false;
                    break;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        characters.append(ch, start, length);
    }

    public void generateHtml() {
        try {
            new File("save").mkdirs();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("save/report_sax.html"), StandardCharsets.UTF_8));

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Отчёт о фотографии дрона (SAX)</title>");
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

            double totalAltitude = 0.0;
            double totalAltitudeTimesSize = 0.0;
            int photoCount = 0;

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

            System.out.println("HTML отчёт сохранён в save/report_sax.html");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double parseFileSize(String fileSizeStr) {
        try {
            if (fileSizeStr.toUpperCase().endsWith("MB")) {
                return Double.parseDouble(fileSizeStr.replace("MB", "").trim());
            }
        } catch (Exception e) {
        }
        return 0.0;
    }
}


