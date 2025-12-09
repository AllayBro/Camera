package org.example.camera.soap.fines.dto;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "GetFinesRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFinesRequest {

    @XmlElement(required = true)
    private String droneId;

    public GetFinesRequest() {}

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }
}
