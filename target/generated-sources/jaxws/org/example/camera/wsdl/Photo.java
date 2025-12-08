
package org.example.camera.wsdl;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DroneID" type="{http://www.w3.org/2001/XMLSchema}IDREF"/>
 *         <element name="TargetID" type="{http://www.w3.org/2001/XMLSchema}IDREF"/>
 *         <element name="DateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         <element name="FilePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="Coordinates" minOccurs="0">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   <element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   <element name="Altitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                 </sequence>
 *                 <attribute name="precision" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="PhotoMetadata">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Resolution" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="FileSize" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="Format" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *       </sequence>
 *       <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "droneID",
    "targetID",
    "dateTime",
    "filePath",
    "coordinates",
    "photoMetadata"
})
@XmlRootElement(name = "Photo", namespace = "http://www.example.com/drone")
public class Photo {

    @XmlElement(name = "DroneID", namespace = "http://www.example.com/drone", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object droneID;
    @XmlElement(name = "TargetID", namespace = "http://www.example.com/drone", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object targetID;
    @XmlElement(name = "DateTime", namespace = "http://www.example.com/drone", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;
    @XmlElement(name = "FilePath", namespace = "http://www.example.com/drone", required = true)
    protected String filePath;
    @XmlElement(name = "Coordinates", namespace = "http://www.example.com/drone")
    protected Photo.Coordinates coordinates;
    @XmlElement(name = "PhotoMetadata", namespace = "http://www.example.com/drone", required = true)
    protected Photo.PhotoMetadata photoMetadata;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the droneID property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDroneID() {
        return droneID;
    }

    /**
     * Sets the value of the droneID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDroneID(Object value) {
        this.droneID = value;
    }

    /**
     * Gets the value of the targetID property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTargetID() {
        return targetID;
    }

    /**
     * Sets the value of the targetID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTargetID(Object value) {
        this.targetID = value;
    }

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the filePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the value of the filePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath(String value) {
        this.filePath = value;
    }

    /**
     * Gets the value of the coordinates property.
     * 
     * @return
     *     possible object is
     *     {@link Photo.Coordinates }
     *     
     */
    public Photo.Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the value of the coordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Photo.Coordinates }
     *     
     */
    public void setCoordinates(Photo.Coordinates value) {
        this.coordinates = value;
    }

    /**
     * Gets the value of the photoMetadata property.
     * 
     * @return
     *     possible object is
     *     {@link Photo.PhotoMetadata }
     *     
     */
    public Photo.PhotoMetadata getPhotoMetadata() {
        return photoMetadata;
    }

    /**
     * Sets the value of the photoMetadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Photo.PhotoMetadata }
     *     
     */
    public void setPhotoMetadata(Photo.PhotoMetadata value) {
        this.photoMetadata = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       <sequence>
     *         <element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         <element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         <element name="Altitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *       </sequence>
     *       <attribute name="precision" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     </restriction>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "latitude",
        "longitude",
        "altitude"
    })
    public static class Coordinates {

        @XmlElement(name = "Latitude", namespace = "http://www.example.com/drone", required = true)
        protected BigDecimal latitude;
        @XmlElement(name = "Longitude", namespace = "http://www.example.com/drone", required = true)
        protected BigDecimal longitude;
        @XmlElement(name = "Altitude", namespace = "http://www.example.com/drone", required = true)
        protected BigDecimal altitude;
        @XmlAttribute(name = "precision")
        protected String precision;

        /**
         * Gets the value of the latitude property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getLatitude() {
            return latitude;
        }

        /**
         * Sets the value of the latitude property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setLatitude(BigDecimal value) {
            this.latitude = value;
        }

        /**
         * Gets the value of the longitude property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getLongitude() {
            return longitude;
        }

        /**
         * Sets the value of the longitude property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setLongitude(BigDecimal value) {
            this.longitude = value;
        }

        /**
         * Gets the value of the altitude property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getAltitude() {
            return altitude;
        }

        /**
         * Sets the value of the altitude property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setAltitude(BigDecimal value) {
            this.altitude = value;
        }

        /**
         * Gets the value of the precision property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrecision() {
            return precision;
        }

        /**
         * Sets the value of the precision property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrecision(String value) {
            this.precision = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>{@code
     * <complexType>
     *   <complexContent>
     *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       <sequence>
     *         <element name="Resolution" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="FileSize" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="Format" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       </sequence>
     *     </restriction>
     *   </complexContent>
     * </complexType>
     * }</pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "resolution",
        "fileSize",
        "format"
    })
    public static class PhotoMetadata {

        @XmlElement(name = "Resolution", namespace = "http://www.example.com/drone", required = true)
        protected String resolution;
        @XmlElement(name = "FileSize", namespace = "http://www.example.com/drone", required = true)
        protected String fileSize;
        @XmlElement(name = "Format", namespace = "http://www.example.com/drone", required = true)
        protected String format;

        /**
         * Gets the value of the resolution property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getResolution() {
            return resolution;
        }

        /**
         * Sets the value of the resolution property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setResolution(String value) {
            this.resolution = value;
        }

        /**
         * Gets the value of the fileSize property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFileSize() {
            return fileSize;
        }

        /**
         * Sets the value of the fileSize property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFileSize(String value) {
            this.fileSize = value;
        }

        /**
         * Gets the value of the format property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFormat() {
            return format;
        }

        /**
         * Sets the value of the format property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFormat(String value) {
            this.format = value;
        }

    }

}
