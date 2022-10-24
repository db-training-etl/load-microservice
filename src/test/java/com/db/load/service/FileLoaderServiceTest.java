package com.db.load.service;

import com.db.load.entity.Trade;
import com.db.load.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileLoaderServiceTest {

    FileLoaderService fileLoaderService;

    @BeforeEach
    void setUp() {
        fileLoaderService = new FileLoaderService(new ObjectMapper());
    }

    @Test
    void load_Bad() {
        assertThrows(NotFoundException.class, () -> fileLoaderService.load("src/test/resources/hola", Trade.class));
    }

    @Test
    void load_Ok() throws IOException {
        List<Trade> trades = fileLoaderService.load("src/test/resources/trade.json", Trade.class);

        assertEquals(1, trades.size());
        assertThat(trades.get(0)).isInstanceOf(Trade.class);
        assertEquals(trades.get(0).getCountry(), "CA");
    }

}