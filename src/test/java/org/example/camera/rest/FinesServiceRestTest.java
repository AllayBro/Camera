package org.example.camera.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FinesServiceRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFines_returnsFinesArray() throws Exception {
        mockMvc.perform(get("/webresources/fines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fines").isArray());
    }

    @Test
    void postFines_calculatesPenalty() throws Exception {
        String body = """
                {
                  "fines": [
                    {
                      "id": "t1",
                      "name": "Test target",
                      "type": "car",
                      "latitude": 56.1,
                      "longitude": 40.3,
                      "altitude": 120.0,
                      "droneId": "d1",
                      "violations": ["Превышение скорости"]
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/webresources/fines")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fines[0].totalPenalty").value(10000));
    }
}
