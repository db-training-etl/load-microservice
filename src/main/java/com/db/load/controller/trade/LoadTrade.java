package com.db.load.controller.trade;

import com.db.load.entity.SuccessCountResponse;
import com.db.load.entity.Trade;
import com.db.load.service.EnrichmentService;
import com.db.load.service.FileLoaderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/trades/load")
public class LoadTrade {

    FileLoaderService fileLoaderService;

    EnrichmentService enrichmentService;

    @PostMapping("")
    public Mono<SuccessCountResponse<Trade>> load(@RequestParam String fileRoute) throws IOException {
        return enrichmentService.enrichTrades(fileLoaderService.load(fileRoute, Trade.class));
    }

}
