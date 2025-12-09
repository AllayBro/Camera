package org.example.camera.soap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FinesServiceSoapTest {

    private static final String SERVICE_URL = "http://localhost:8090/camera2/FinesService";

    @Test
    void registerFine_and_getFines_work() throws Exception {
        String registerEnvelope = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:fin="http://www.example.com/fines/service">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <fin:RegisterFineRequest>
                      <droneId>d1</droneId>
                      <violation>Превышение скорости</violation>
                      <penalty>10000.0</penalty>
                    </fin:RegisterFineRequest>
                  </soapenv:Body>
                </soapenv:Envelope>
                """;

        sendSoap(SERVICE_URL, registerEnvelope,
                "http://www.example.com/fines/service/RegisterFine");

        String getEnvelope = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:fin="http://www.example.com/fines/service">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <fin:GetFinesRequest>
                      <droneId>d1</droneId>
                    </fin:GetFinesRequest>
                  </soapenv:Body>
                </soapenv:Envelope>
                """;

        String response = sendSoap(SERVICE_URL, getEnvelope,
                "http://www.example.com/fines/service/GetFines");

        assertTrue(response.contains("GetFinesResponse"));
        assertTrue(response.contains("Превышение скорости"));
    }

    private String sendSoap(String url, String body, String soapAction) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        conn.setRequestProperty("SOAPAction", "\"" + soapAction + "\"");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        InputStream is = status >= 200 && status < 300
                ? conn.getInputStream()
                : conn.getErrorStream();

        byte[] bytes = is.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
