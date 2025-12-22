package org.example.camera.soap.fines.dto;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "RegisterFineResponse", namespace = "http://www.example.com/fines/service")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterFineResponse {

    @XmlElement(required = true)
    private String result;

    public RegisterFineResponse() {}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
