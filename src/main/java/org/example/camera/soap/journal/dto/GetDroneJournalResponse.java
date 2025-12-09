package org.example.camera.soap.journal.dto;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "GetDroneJournalResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetDroneJournalResponse {

    @XmlElement(name = "record")
    private List<JournalRecord> records;

    public GetDroneJournalResponse() {}

    public List<JournalRecord> getRecords() {
        return records;
    }

    public void setRecords(List<JournalRecord> records) {
        this.records = records;
    }
}
