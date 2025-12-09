package org.example.camera.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JournalServiceRestTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getJournal_returnsArray() throws Exception {
        mockMvc.perform(get("/camera2/webresources/journal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.journal").isArray());
    }
}
