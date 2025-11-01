package org.example.camera;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.XMLConstants;
import org.xml.sax.SAXException;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;
import java.io.*;
import java.util.*;

public class DronePhotoJSONAnalyzer {

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

            // Инициализация JAXB
            JAXBContext context = JAXBContext.newInstance(DroneTarget.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Валидация по XSD
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(xsdPath));
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new ValidationEventHandler() {
                @Override
                public boolean handleEvent(ValidationEvent event) {
                    System.err.println("[VALIDATION WARNING] " + event.getMessage());
                    return event.getSeverity() != ValidationEvent.FATAL_ERROR;
                }
            });

            // Парсинг XML
            DroneTarget droneTarget = (DroneTarget) unmarshaller.unmarshal(new File(xmlPath));

            // Модификация данных
            modifyGraph(droneTarget);

            // Генерация JSON
            generateJson(droneTarget);

            System.out.println("JSON отчет успешно сохранен в save/report_json.json");


        } catch (SAXException e) {
            System.err.println("[ERROR] Ошибка схемы: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("[ERROR] Ошибка обработки: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void modifyGraph(DroneTarget droneTarget) {
        // Добавляем новую фотографию
        Photo newPhoto = new Photo();
        newPhoto.setId("p_new");
        newPhoto.setDroneID("d1");
        newPhoto.setTargetID("t2");
        newPhoto.setDateTime("2025-07-01T16:00:00Z");
        newPhoto.setFilePath("/photos/added_photo.jpg");

        PhotoMetadata metadata = new PhotoMetadata();
        metadata.setResolution("4096x2160");
        metadata.setFileSize("4.2MB");
        metadata.setFormat("JPEG");

        newPhoto.setPhotoMetadata(metadata);

        droneTarget.getPhotos().add(newPhoto);

        System.out.println("🛠 Добавлена новая фотография для JSON-графа.");
    }

    private static void generateJson(DroneTarget droneTarget) throws IOException {
        File saveDir = new File("save");
        saveDir.mkdirs();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Красивое форматирование JSON

        AggregatedResult result = new AggregatedResult();
        result.setPhotos(droneTarget.getPhotos());
        result.calculateTotals();

        File outputFile = new File(saveDir, "report_json.json");
        mapper.writeValue(outputFile, result);

        // Автоматическое открытие
        try {
            Runtime.getRuntime().exec(new String[] {
                    "cmd", "/c", "start", outputFile.getAbsolutePath()
            });
        } catch (IOException e) {
            System.err.println("Не удалось автоматически открыть JSON-файл: " + e.getMessage());
        }
    }

    // ======= Классы модели (JAXB + JSON) =======

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
        public void setDrones(List<Drone> drones) { this.drones = drones; }

        public List<Target> getTargets() { return targets; }
        public void setTargets(List<Target> targets) { this.targets = targets; }

        public List<Photo> getPhotos() { return photos; }
        public void setPhotos(List<Photo> photos) { this.photos = photos; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Drone {
        @XmlAttribute
        private String id;
        @XmlAttribute
        private String model;
        @XmlAttribute
        private String status;
        @XmlElement(name = "Model", namespace = "http://www.example.com/drone")
        private String modelText;
        @XmlElement(name = "Status", namespace = "http://www.example.com/drone")
        private String statusText;
        // Геттеры и сеттеры
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Target {
        @XmlAttribute
        private String id;
        @XmlAttribute
        private String name;
        @XmlAttribute
        private String type;
        @XmlElement(name = "Name", namespace = "http://www.example.com/drone")
        private String nameText;
        @XmlElement(name = "Type", namespace = "http://www.example.com/drone")
        private String typeText;
        @XmlElement(name = "Coordinates", namespace = "http://www.example.com/drone")
        private Coordinates coordinates;
        // Геттеры и сеттеры
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
        @XmlAttribute
        private String precision;
        @XmlElement(name = "Latitude", namespace = "http://www.example.com/drone")
        private double latitude;
        @XmlElement(name = "Longitude", namespace = "http://www.example.com/drone")
        private double longitude;
        @XmlElement(name = "Altitude", namespace = "http://www.example.com/drone")
        private double altitude;
        // Геттеры и сеттеры
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
        @XmlAttribute
        private String id;
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

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
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

    // ======= Класс для агрегации данных (для JSON отчета) =======
    public static class AggregatedResult {
        private List<Photo> photos;
        private double totalFileSizeMB;
        private int photoCount;

        public List<Photo> getPhotos() { return photos; }
        public void setPhotos(List<Photo> photos) { this.photos = photos; }

        public double getTotalFileSizeMB() { return totalFileSizeMB; }
        public int getPhotoCount() { return photoCount; }

        public void calculateTotals() {
            totalFileSizeMB = 0;
            photoCount = photos.size();
            for (Photo p : photos) {
                if (p.getPhotoMetadata() != null) {
                    try {
                        String fs = p.getPhotoMetadata().getFileSize().replace("MB", "").trim();
                        totalFileSizeMB += Double.parseDouble(fs);
                    } catch (Exception ignored) {}
                }
            }
        }
    }
}


