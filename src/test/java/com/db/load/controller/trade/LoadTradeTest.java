package com.db.load.controller.trade;

import com.db.load.entity.Trade;
import com.db.load.service.EnrichmentService;
import com.db.load.service.FileLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadTradeTest {

    LoadTrade loadTradeController;

    @Mock
    FileLoaderService fileLoaderService;

    @Mock
    EnrichmentService enrichmentService;

    @BeforeEach
    void setUp() throws IOException {
        loadTradeController = new LoadTrade(fileLoaderService, enrichmentService);
        when(fileLoaderService.load(anyString(), eq(Trade.class))).thenReturn(List.of(new Trade()));
    }

    @Test
    void load_ok() throws IOException {
        loadTradeController.load("fileRoute");
        verify(fileLoaderService).load("fileRoute", Trade.class);
    }
}