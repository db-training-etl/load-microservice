package com.db.load.controller.book;

import com.db.load.entity.Book;
import com.db.load.service.FileLoaderService;
import com.db.load.service.ReferenceDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books/load")
public class LoadBook {

    FileLoaderService fileLoaderService;
    ReferenceDataService referenceDataService;

    @PostMapping("")
    public Mono<List<Book>> load(@RequestParam String fileRoute) throws IOException {
        return Flux.fromIterable(fileLoaderService.load(fileRoute, Book.class))
                .flatMap(referenceDataService::saveBook)
                .collectList();
    }

}
