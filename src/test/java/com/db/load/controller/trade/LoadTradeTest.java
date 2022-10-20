package com.db.load.controller.trade;

import com.db.load.entity.Trade;
import com.db.load.service.FileLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoadTradeTest {

    LoadTrade loadTradeController;

    @Mock
    FileLoaderService fileLoaderService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this); //without this you will get NPE

        loadTradeController = new LoadTrade(fileLoaderService);
        when(fileLoaderService.load(anyString(), eq(Trade.class))).thenReturn(List.of(new Trade()));
    }

    @Test
    void load() throws IOException {
        loadTradeController.load("fileRoute");
        verify(fileLoaderService).load("fileRoute", Trade.class);
    }
}