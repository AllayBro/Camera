package org.example.camera.soap.fines.dto;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "GetFinesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFinesResponse {

    @XmlElement(name = "fine")
    private List<String> fines;

    public GetFinesResponse() {}

    public List<String> getFines() {
        return fines;
    }

    public void setFines(List<String> fines) {
        this.fines = fines;
    }
}
