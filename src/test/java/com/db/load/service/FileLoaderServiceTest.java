package com.db.load.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderServiceTest {

    @Test
    void load_Ok() {
        FileLoaderService fileLoaderService = new FileLoaderService();
        fileLoaderService.load("/home/");
    }
}