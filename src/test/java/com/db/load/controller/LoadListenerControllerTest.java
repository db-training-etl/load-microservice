package com.db.load.controller;

import com.db.load.service.FileLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoadListenerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FileLoaderService fileLoaderService;

    @Test
    void load_ok() throws Exception {
        mockMvc.perform(post("/load")
                        .param("fileRoute", "/home/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(fileLoaderService).load("/home/");
    }

    @Test
    void load_badRequest() throws Exception {
        mockMvc.perform(post("/load")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}