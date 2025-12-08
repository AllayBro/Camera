
package org.example.camera.wsdl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
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
 *         <element name="Drone" maxOccurs="unbounded">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Model" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="Photos" minOccurs="0">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                 </sequence>
 *                 <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="Target" maxOccurs="unbounded">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   <element name="Coordinates">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             <element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             <element name="Altitude" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                           </sequence>
 *                           <attribute name="precision" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                   <element name="Photos" minOccurs="0">
 *                     <complexType>
 *                       <complexContent>
 *                         <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           <sequence>
 *                             <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
 *                           </sequence>
 *                         </restriction>
 *                       </complexContent>
 *                     </complexType>
 *                   </element>
 *                 </sequence>
 *                 <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *         <element name="PhotoDatabase">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <sequence>
 *                   <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
 *                 </sequence>
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
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
    "drone",
    "target",
    "photoDatabase"
})
@XmlRootElement(name = "DroneTarget", namespace = "http://www.example.com/drone")
public class DroneTarget {

    @XmlElement(name = "Drone", namespace = "http://www.example.com/drone", required = true)
    protected List<DroneTarget.Drone> drone;
    @XmlElement(name = "Target", namespace = "http://www.example.com/drone", required = true)
    protected List<DroneTarget.Target> target;
    @XmlElement(name = "PhotoDatabase", namespace = "http://www.example.com/drone", required = true)
    protected DroneTarget.PhotoDatabase photoDatabase;

    /**
     * Gets the value of the drone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the drone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DroneTarget.Drone }
     * 
     * 
     * @return
     *     The value of the drone property.
     */
    public List<DroneTarget.Drone> getDrone() {
        if (drone == null) {
            drone = new ArrayList<>();
        }
        return this.drone;
    }

    /**
     * Gets the value of the target property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the target property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTarget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DroneTarget.Target }
     * 
     * 
     * @return
     *     The value of the target property.
     */
    public List<DroneTarget.Target> getTarget() {
        if (target == null) {
            target = new ArrayList<>();
        }
        return this.target;
    }

    /**
     * Gets the value of the photoDatabase property.
     * 
     * @return
     *     possible object is
     *     {@link DroneTarget.PhotoDatabase }
     *     
     */
    public DroneTarget.PhotoDatabase getPhotoDatabase() {
        return photoDatabase;
    }

    /**
     * Sets the value of the photoDatabase property.
     * 
     * @param value
     *     allowed object is
     *     {@link DroneTarget.PhotoDatabase }
     *     
     */
    public void setPhotoDatabase(DroneTarget.PhotoDatabase value) {
        this.photoDatabase = value;
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
     *         <element name="Model" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="Photos" minOccurs="0">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
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
        "model",
        "status",
        "photos"
    })
    public static class Drone {

        @XmlElement(name = "Model", namespace = "http://www.example.com/drone", required = true)
        protected String model;
        @XmlElement(name = "Status", namespace = "http://www.example.com/drone", required = true)
        protected String status;
        @XmlElement(name = "Photos", namespace = "http://www.example.com/drone")
        protected DroneTarget.Drone.Photos photos;
        @XmlAttribute(name = "id", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;

        /**
         * Gets the value of the model property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getModel() {
            return model;
        }

        /**
         * Sets the value of the model property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setModel(String value) {
            this.model = value;
        }

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the photos property.
         * 
         * @return
         *     possible object is
         *     {@link DroneTarget.Drone.Photos }
         *     
         */
        public DroneTarget.Drone.Photos getPhotos() {
            return photos;
        }

        /**
         * Sets the value of the photos property.
         * 
         * @param value
         *     allowed object is
         *     {@link DroneTarget.Drone.Photos }
         *     
         */
        public void setPhotos(DroneTarget.Drone.Photos value) {
            this.photos = value;
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
         *         <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
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
            "photo"
        })
        public static class Photos {

            @XmlElement(name = "Photo", namespace = "http://www.example.com/drone", required = true)
            protected List<Photo> photo;

            /**
             * Gets the value of the photo property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the Jakarta XML Binding object.
             * This is why there is not a {@code set} method for the photo property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPhoto().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Photo }
             * 
             * 
             * @return
             *     The value of the photo property.
             */
            public List<Photo> getPhoto() {
                if (photo == null) {
                    photo = new ArrayList<>();
                }
                return this.photo;
            }

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
     *         <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
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
        "photo"
    })
    public static class PhotoDatabase {

        @XmlElement(name = "Photo", namespace = "http://www.example.com/drone", required = true)
        protected List<Photo> photo;

        /**
         * Gets the value of the photo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a {@code set} method for the photo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Photo }
         * 
         * 
         * @return
         *     The value of the photo property.
         */
        public List<Photo> getPhoto() {
            if (photo == null) {
                photo = new ArrayList<>();
            }
            return this.photo;
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
     *         <element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         <element name="Coordinates">
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
     *         <element name="Photos" minOccurs="0">
     *           <complexType>
     *             <complexContent>
     *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 <sequence>
     *                   <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
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
        "name",
        "type",
        "coordinates",
        "photos"
    })
    public static class Target {

        @XmlElement(name = "Name", namespace = "http://www.example.com/drone", required = true)
        protected String name;
        @XmlElement(name = "Type", namespace = "http://www.example.com/drone", required = true)
        protected String type;
        @XmlElement(name = "Coordinates", namespace = "http://www.example.com/drone", required = true)
        protected DroneTarget.Target.Coordinates coordinates;
        @XmlElement(name = "Photos", namespace = "http://www.example.com/drone")
        protected DroneTarget.Target.Photos photos;
        @XmlAttribute(name = "id", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String id;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the coordinates property.
         * 
         * @return
         *     possible object is
         *     {@link DroneTarget.Target.Coordinates }
         *     
         */
        public DroneTarget.Target.Coordinates getCoordinates() {
            return coordinates;
        }

        /**
         * Sets the value of the coordinates property.
         * 
         * @param value
         *     allowed object is
         *     {@link DroneTarget.Target.Coordinates }
         *     
         */
        public void setCoordinates(DroneTarget.Target.Coordinates value) {
            this.coordinates = value;
        }

        /**
         * Gets the value of the photos property.
         * 
         * @return
         *     possible object is
         *     {@link DroneTarget.Target.Photos }
         *     
         */
        public DroneTarget.Target.Photos getPhotos() {
            return photos;
        }

        /**
         * Sets the value of the photos property.
         * 
         * @param value
         *     allowed object is
         *     {@link DroneTarget.Target.Photos }
         *     
         */
        public void setPhotos(DroneTarget.Target.Photos value) {
            this.photos = value;
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
         *         <element ref="{http://www.example.com/drone}Photo" maxOccurs="unbounded"/>
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
            "photo"
        })
        public static class Photos {

            @XmlElement(name = "Photo", namespace = "http://www.example.com/drone", required = true)
            protected List<Photo> photo;

            /**
             * Gets the value of the photo property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the Jakarta XML Binding object.
             * This is why there is not a {@code set} method for the photo property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPhoto().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Photo }
             * 
             * 
             * @return
             *     The value of the photo property.
             */
            public List<Photo> getPhoto() {
                if (photo == null) {
                    photo = new ArrayList<>();
                }
                return this.photo;
            }

        }

    }

}
