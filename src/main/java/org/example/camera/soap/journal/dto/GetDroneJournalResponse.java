package org.example.camera.soap.journal.dto;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "GetDroneJournalResponse", namespace = "http://www.example.com/journal/service")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDroneJournalResponse {

    @XmlElement(name = "record", namespace = "http://www.example.com/journal/service")
    private List<JournalRecord> records = new java.util.ArrayList<>();

    public GetDroneJournalResponse() {}

    public List<JournalRecord> getRecords() {
        return records;
    }

    public void setRecords(List<JournalRecord> records) {
        this.records = records;
    }
}
