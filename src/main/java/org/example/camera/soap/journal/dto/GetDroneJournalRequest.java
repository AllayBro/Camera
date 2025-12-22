package org.example.camera.soap.journal.dto;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "GetDroneJournalRequest", namespace = "http://www.example.com/journal/service")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDroneJournalRequest {

    @XmlElement(required = true)
    private String droneId;

    public GetDroneJournalRequest() {}

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }
}
