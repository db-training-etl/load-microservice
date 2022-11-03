package com.db.load.service;

import com.db.load.entity.ListResponse;
import com.db.load.entity.Trade;
import com.db.load.repository.EnrichmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnrichmentService {
    EnrichmentRepository enrichmentRepository;

    public Mono<ListResponse<Trade>> enrichTrades(List<Trade> trades) {
        return Flux.fromIterable(trades)
                .flatMap(enrichmentRepository::enrich)
                .filter(tradeResponseEntity -> tradeResponseEntity.getStatusCode().is2xxSuccessful())
                .collectList()
                .map((successTrades) -> new ListResponse<>(
                        "Trades enriched successfully",
                        successTrades.size(),
                        successTrades.stream().map(
                                tradeResponseEntity -> Optional.ofNullable(tradeResponseEntity.getBody()).orElse(new Trade())
                        ).toList(),
                        trades.size() - successTrades.size()
                ));
    }
}
