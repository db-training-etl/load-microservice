package com.db.load.controller.counterparty;

import com.db.load.entity.Counterparty;
import com.db.load.service.FileLoaderService;
import com.db.load.service.ReferenceDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/counterparties/load")
public class LoadCounterparty {

    FileLoaderService fileLoaderService;

    ReferenceDataService referenceDataService;

    @PostMapping("")
    public Mono<List<Counterparty>> load(@RequestParam String fileRoute) throws IOException {
        return Flux.fromIterable(fileLoaderService.load(fileRoute, Counterparty.class))
                .flatMap(referenceDataService::saveCounterparty)
                .collectList();
    }

}
