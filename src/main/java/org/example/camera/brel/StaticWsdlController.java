package org.example.camera.brel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class StaticWsdlController {

    @GetMapping(value = "/services/DroneFineProcess", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<byte[]> getDroneFineWsdl(@RequestParam(required = false) String wsdl) throws IOException {
        // если нужен только ?wsdl — можно проверять параметр, но не обязательно
        ClassPathResource r = new ClassPathResource("wsdl/DroneFineProcess.wsdl");
        if (!r.exists()) {
            return ResponseEntity.notFound().build();
        }
        byte[] content = r.getInputStream().readAllBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/xml; charset=UTF-8")
                .body(content);
    }
}
