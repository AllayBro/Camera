
package org.example.camera.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


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
 *         <element name="droneInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "droneInfo"
})
@XmlRootElement(name = "GetDroneInfoResponse")
public class GetDroneInfoResponse {

    @XmlElement(required = true)
    protected String droneInfo;

    /**
     * Gets the value of the droneInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDroneInfo() {
        return droneInfo;
    }

    /**
     * Sets the value of the droneInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDroneInfo(String value) {
        this.droneInfo = value;
    }

}
