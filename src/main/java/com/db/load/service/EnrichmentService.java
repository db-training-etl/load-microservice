package com.db.load.service;

import com.db.load.entity.SuccessCountResponse;
import com.db.load.entity.Trade;
import com.db.load.repository.EnrichmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnrichmentService {
    EnrichmentRepository enrichmentRepository;

    public Mono<SuccessCountResponse<Trade>> enrichTrades(List<Trade> trade) {
        return Flux.fromIterable(trade)
                .flatMap(enrichmentRepository::enrich)
                .filter(tradeResponseEntity -> tradeResponseEntity.getStatusCode().is2xxSuccessful())
                .collectList()
                .map((trades) -> new SuccessCountResponse<>(
                        "Trades enriched successfully",
                        trades.size(),
                        trades.stream().map(
                                tradeResponseEntity -> Optional.ofNullable(tradeResponseEntity.getBody()).orElse(new Trade())
                        ).toList()
                ));
    }
}
