package com.db.load.controller.counterparty;

import com.db.load.entity.Counterparty;
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

class LoadBookTest {

    LoadCounterparty loadCounterpartyController;

    @Mock
    FileLoaderService fileLoaderService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this); //without this you will get NPE

        loadCounterpartyController = new LoadCounterparty(fileLoaderService);
        when(fileLoaderService.load(anyString(), eq(Counterparty.class))).thenReturn(List.of(new Counterparty()));
    }

    @Test
    void load() throws IOException {
        loadCounterpartyController.load("fileRoute");
        verify(fileLoaderService).load("fileRoute", Counterparty.class);
    }
}