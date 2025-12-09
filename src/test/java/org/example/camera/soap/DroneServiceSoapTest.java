package org.example.camera.soap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DroneServiceSoapTest {

    @Test
    void getDroneInfo_works() throws Exception {

        String envelope = """
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                          xmlns:dr="http://www.example.com/drone/service">
          <soapenv:Header/>
          <soapenv:Body>
            <dr:GetDroneInfoRequest>
              <droneId>d1</droneId>
            </dr:GetDroneInfoRequest>
          </soapenv:Body>
        </soapenv:Envelope>
        """;

        String resp = sendSoap("http://localhost:8090/camera2/DroneService",
                envelope,
                "http://www.example.com/drone/service/GetDroneInfo");

        assertTrue(resp.contains("GetDroneInfoResponse"));
    }

    private String sendSoap(String url, String xml, String soapAction) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setRequestMethod("POST");
        c.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        c.setRequestProperty("SOAPAction", "\"" + soapAction + "\"");
        c.setDoOutput(true);

        try (OutputStream os = c.getOutputStream()) {
            os.write(xml.getBytes(StandardCharsets.UTF_8));
        }

        InputStream is = c.getResponseCode() >= 200 ? c.getInputStream() : c.getErrorStream();
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
}
