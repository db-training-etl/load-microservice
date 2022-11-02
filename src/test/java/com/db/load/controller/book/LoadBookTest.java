package com.db.load.controller.book;

import com.db.load.controller.counterparty.LoadCounterparty;
import com.db.load.entity.Book;
import com.db.load.entity.Counterparty;
import com.db.load.entity.Trade;
import com.db.load.service.FileLoaderService;
import com.db.load.service.ReferenceDataService;
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
class LoadBookTest {

    LoadBook loadBookController;

    @Mock
    FileLoaderService fileLoaderService;

    @Mock
    ReferenceDataService referenceDataService;

    @BeforeEach
    void setUp() throws IOException {
        loadBookController = new LoadBook(fileLoaderService, referenceDataService);
        when(fileLoaderService.load(anyString(), eq(Book.class))).thenReturn(List.of(new Book()));
    }

    @Test
    void load() throws IOException {
        loadBookController.load("fileRoute");
        verify(fileLoaderService).load("fileRoute", Book.class);
    }
}