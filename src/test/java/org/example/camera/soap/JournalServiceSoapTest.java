package org.example.camera.soap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JournalServiceSoapTest {

    @Test
    void getJournal_works() throws Exception {

        String envelope = """
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                          xmlns:j="http://www.example.com/journal/service">
          <soapenv:Header/>
          <soapenv:Body>
            <j:GetDroneJournalRequest>
              <droneId>d1</droneId>
            </j:GetDroneJournalRequest>
          </soapenv:Body>
        </soapenv:Envelope>
        """;

        String resp = sendSoap(
                "http://localhost:8090/camera2/JournalService",
                envelope,
                "http://www.example.com/journal/service/GetDroneJournal");

        assertTrue(resp.contains("GetDroneJournalResponse"));
    }

    private String sendSoap(String url, String body, String action) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setRequestMethod("POST");
        c.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        c.setRequestProperty("SOAPAction", "\"" + action + "\"");
        c.setDoOutput(true);

        try (OutputStream os = c.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        InputStream is = c.getInputStream();
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
}
