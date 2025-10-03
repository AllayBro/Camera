package main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class DronePhotoJAXBAnalyzer {

    public static void main(String[] args) {
        try {
            String xmlPath;
            if (args.length > 0) {
                xmlPath = args[0];
            } else {
                // Используем файл по умолчанию из ресурсов
                File defaultFile = new File("src/main/resources/example.xml");
                if (defaultFile.exists()) {
                    xmlPath = defaultFile.getAbsolutePath();
                    System.out.println("Используется файл по умолчанию: " + xmlPath);
                } else {
                    System.err.println("Ошибка: не указан путь к XML-файлу и файл по умолчанию не найден.");
                    return;
                }
            }

            String xsdPath = "src/main/resources/drone_target.xsd";

            JAXBContext context = JAXBContext.newInstance(DroneTarget.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdPath));
            unmarshaller.setSchema(schema);

            DroneTarget droneTarget = (DroneTarget) unmarshaller.unmarshal(new File(xmlPath));

            modifyGraph(droneTarget);
            generateHtml(droneTarget);

            File saveDir = new File("save");
            saveDir.mkdirs();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(droneTarget, new File(saveDir, "updated_example.xml"));

            System.out.println("HTML отчет успешно сохранен в save/report_jaxb.html");

        } catch (SAXException e) {
            System.err.println("Ошибка схемы: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ошибка обработки: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void modifyGraph(DroneTarget droneTarget) {
        Photo newPhoto = new Photo();
        newPhoto.setId("p_new");
        newPhoto.setDroneID("d1");
        newPhoto.setTargetID("t1");
        newPhoto.setDateTime("2025-05-01T15:00:00Z");
        newPhoto.setFilePath("/photos/new_photo.jpg");

        PhotoMetadata metadata = new PhotoMetadata();
        metadata.setResolution("3840x2160");
        metadata.setFileSize("3.5MB");
        metadata.setFormat("JPEG");
        newPhoto.setPhotoMetadata(metadata);

        droneTarget.getPhotos().add(newPhoto);

        System.out.println("🛠 Новая фотография добавлена в граф объектов.");
    }

    private static void generateHtml(DroneTarget droneTarget) {
        try {
            File saveDir = new File("save");
            saveDir.mkdirs();
            PrintWriter out = new PrintWriter(new FileWriter(new File(saveDir, "report_jaxb.html")));

            out.println("<html><head><title>Отчет о фотографиях дронов (JAXB)</title></head><body>");
            out.println("<h1>Фотографии дронов</h1>");
            out.println("<table border='1'>");
            out.println("<thead><tr><th>Drone ID</th><th>Target ID</th><th>Date/Time</th><th>Latitude</th><th>Longitude</th><th>Altitude</th><th>File Size (MB)</th><th>File Size (Bytes)</th></tr></thead>");
            out.println("<tbody>");

            double totalAltitude = 0.0;
            int count = 0;

            for (Photo photo : droneTarget.getPhotos()) {
                Target target = droneTarget.getTargetById(photo.getTargetID());
                if (target != null) {
                    double latitude = target.getCoordinates().getLatitude();
                    double longitude = target.getCoordinates().getLongitude();
                    double altitude = target.getCoordinates().getAltitude();

                    double fileSizeMB = parseFileSize(photo.getPhotoMetadata().getFileSize());
                    double fileSizeBytes = fileSizeMB * 1024 * 1024;

                    out.printf("<tr><td>%s</td><td>%s</td><td>%s</td><td>%.4f</td><td>%.4f</td><td>%.2f</td><td>%.2f</td><td>%.0f</td></tr>\n",
                            photo.getDroneID(), photo.getTargetID(), photo.getDateTime(), latitude, longitude, altitude, fileSizeMB, fileSizeBytes);

                    totalAltitude += altitude;
                    count++;
                }
            }

            out.println("</tbody>");
            out.println("<tfoot>");
            if (count > 0) {
                double avgAltitude = totalAltitude / count;
                out.printf("<tr><td colspan='5'><b>Средняя высота целей</b></td><td colspan='3'>%.2f</td></tr>\n", avgAltitude);
            }
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</body></html>");
            out.flush();
            out.close();

        } catch (IOException e) {
            System.err.println("Ошибка записи отчета: " + e.getMessage());
        }
    }

    private static double parseFileSize(String fileSizeStr) {
        try {
            if (fileSizeStr.toUpperCase().endsWith("MB")) {
                return Double.parseDouble(fileSizeStr.replace("MB", "").trim());
            }
        } catch (Exception ignored) {
        }
        return 0.0;
    }

    // ====== JAXB Classes ======

    @XmlRootElement(name = "DroneTarget", namespace = "http://www.example.com/drone")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DroneTarget {
        @XmlElement(name = "Drone", namespace = "http://www.example.com/drone")
        private List<Drone> drones;
        @XmlElement(name = "Target", namespace = "http://www.example.com/drone")
        private List<Target> targets;
        @XmlElement(name = "Photo", namespace = "http://www.example.com/drone")
        private List<Photo> photos;

        public List<Drone> getDrones() { return drones; }
        public List<Target> getTargets() { return targets; }
        public List<Photo> getPhotos() { return photos; }

        public void setDrones(List<Drone> drones) { this.drones = drones; }
        public void setTargets(List<Target> targets) { this.targets = targets; }
        public void setPhotos(List<Photo> photos) { this.photos = photos; }

        public Target getTargetById(String id) {
            if (targets != null) {
                for (Target t : targets) {
                    if (t.getId().equals(id)) {
                        return t;
                    }
                }
            }
            return null;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Drone {
        @XmlAttribute private String id;
        @XmlAttribute private String model;
        @XmlAttribute private String status;
        @XmlElement(name = "Model", namespace = "http://www.example.com/drone")
        private String modelText;
        @XmlElement(name = "Status", namespace = "http://www.example.com/drone")
        private String statusText;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Target {
        @XmlAttribute private String id;
        @XmlAttribute private String name;
        @XmlAttribute private String type;
        @XmlElement(name = "Name", namespace = "http://www.example.com/drone")
        private String nameText;
        @XmlElement(name = "Type", namespace = "http://www.example.com/drone")
        private String typeText;
        @XmlElement(name = "Coordinates", namespace = "http://www.example.com/drone")
        private Coordinates coordinates;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Coordinates getCoordinates() { return coordinates; }
        public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Coordinates {
        @XmlAttribute private String precision;
        @XmlElement(name = "Latitude", namespace = "http://www.example.com/drone")
        private double latitude;
        @XmlElement(name = "Longitude", namespace = "http://www.example.com/drone")
        private double longitude;
        @XmlElement(name = "Altitude", namespace = "http://www.example.com/drone")
        private double altitude;

        public String getPrecision() { return precision; }
        public void setPrecision(String precision) { this.precision = precision; }
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
        public double getAltitude() { return altitude; }
        public void setAltitude(double altitude) { this.altitude = altitude; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Photo {
        @XmlAttribute private String id;
        @XmlElement(name = "DroneID", namespace = "http://www.example.com/drone")
        private String droneID;
        @XmlElement(name = "TargetID", namespace = "http://www.example.com/drone")
        private String targetID;
        @XmlElement(name = "DateTime", namespace = "http://www.example.com/drone")
        private String dateTime;
        @XmlElement(name = "FilePath", namespace = "http://www.example.com/drone")
        private String filePath;
        @XmlElement(name = "PhotoMetadata", namespace = "http://www.example.com/drone")
        private PhotoMetadata photoMetadata;

        public String getDroneID() { return droneID; }
        public void setDroneID(String droneID) { this.droneID = droneID; }
        public String getTargetID() { return targetID; }
        public void setTargetID(String targetID) { this.targetID = targetID; }
        public String getDateTime() { return dateTime; }
        public void setDateTime(String dateTime) { this.dateTime = dateTime; }
        public String getFilePath() { return filePath; }
        public void setFilePath(String filePath) { this.filePath = filePath; }
        public PhotoMetadata getPhotoMetadata() { return photoMetadata; }
        public void setPhotoMetadata(PhotoMetadata photoMetadata) { this.photoMetadata = photoMetadata; }
        public void setId(String id) { this.id = id; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PhotoMetadata {
        @XmlElement(name = "Resolution", namespace = "http://www.example.com/drone")
        private String resolution;
        @XmlElement(name = "FileSize", namespace = "http://www.example.com/drone")
        private String fileSize;
        @XmlElement(name = "Format", namespace = "http://www.example.com/drone")
        private String format;

        public String getResolution() { return resolution; }
        public void setResolution(String resolution) { this.resolution = resolution; }
        public String getFileSize() { return fileSize; }
        public void setFileSize(String fileSize) { this.fileSize = fileSize; }
        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }
    }
}


