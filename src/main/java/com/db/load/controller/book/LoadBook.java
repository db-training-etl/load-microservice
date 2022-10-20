package com.db.load.controller.book;

import com.db.load.entity.Book;
import com.db.load.service.FileLoaderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/books/loadFromDirectory")
public class LoadBook {

    FileLoaderService fileLoaderService;

    @PostMapping("")
    public void load(@RequestParam String fileRoute) throws IOException {
        fileLoaderService.load(fileRoute, Book.class);
    }

}
