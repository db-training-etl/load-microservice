package com.db.load.controller.counterparty;

import com.db.load.entity.Counterparty;
import com.db.load.service.FileLoaderService;
import com.db.load.service.ReferenceDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadCounterpartyTest {

    LoadCounterparty loadCounterpartyController;

    @Mock
    FileLoaderService fileLoaderService;

    @Mock
    ReferenceDataService referenceDataService;

    @BeforeEach
    void setUp() throws IOException {
        loadCounterpartyController = new LoadCounterparty(fileLoaderService, referenceDataService);
        when(fileLoaderService.load(anyString(), eq(Counterparty.class))).thenReturn(List.of(new Counterparty()));
    }

    @Test
    void load() throws IOException {
        loadCounterpartyController.load("fileRoute");
        verify(fileLoaderService).load("fileRoute", Counterparty.class);
    }
}