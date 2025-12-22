package org.example.camera.soap.fines.dto;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "RegisterFineRequest", namespace = "http://www.example.com/fines/service")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterFineRequest {

    @XmlElement(required = true)
    private String droneId;

    @XmlElement(required = true)
    private String violation;

    @XmlElement(required = true)
    private double penalty;

    public RegisterFineRequest() {}

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }
}
