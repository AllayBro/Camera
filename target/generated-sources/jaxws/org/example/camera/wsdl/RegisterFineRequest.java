
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
 *         <element name="droneId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="violation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="penalty" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "droneId",
    "violation",
    "penalty"
})
@XmlRootElement(name = "RegisterFineRequest")
public class RegisterFineRequest {

    @XmlElement(required = true)
    protected String droneId;
    @XmlElement(required = true)
    protected String violation;
    protected double penalty;

    /**
     * Gets the value of the droneId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDroneId() {
        return droneId;
    }

    /**
     * Sets the value of the droneId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDroneId(String value) {
        this.droneId = value;
    }

    /**
     * Gets the value of the violation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViolation() {
        return violation;
    }

    /**
     * Sets the value of the violation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViolation(String value) {
        this.violation = value;
    }

    /**
     * Gets the value of the penalty property.
     * 
     */
    public double getPenalty() {
        return penalty;
    }

    /**
     * Sets the value of the penalty property.
     * 
     */
    public void setPenalty(double value) {
        this.penalty = value;
    }

}
