package org.example.camera.soap.fines.dto;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "GetFinesResponse", namespace = "http://www.example.com/fines/service")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetFinesResponse {

    @XmlElement(name = "fine", namespace = "http://www.example.com/fines/service")
    private List<String> fines = new java.util.ArrayList<>();

    public GetFinesResponse() {}

    public List<String> getFines() {
        return fines;
    }

    public void setFines(List<String> fines) {
        this.fines = fines;
    }
}
