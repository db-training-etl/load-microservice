package com.db.load.controller.trade;

import com.db.load.entity.Trade;
import com.db.load.service.FileLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoadTradeTestIT {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    FileLoaderService fileLoaderService;

    @Test
    void load_ok() throws Exception {
        mockMvc.perform(post("/trades/load")
                        .param("fileRoute", "src/test/resources/trade.json")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(fileLoaderService).load("src/test/resources/trade.json", Trade.class);
    }

    @Test
    void load_badRequest() throws Exception {
        mockMvc.perform(post("/trades/load")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void load_notFound() throws Exception {
        mockMvc.perform(post("/trades/load")
                        .param("fileRoute", "src/test/resources/tradee.json")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}