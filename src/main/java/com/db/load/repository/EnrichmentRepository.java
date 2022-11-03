package com.db.load.repository;

import com.db.load.entity.Trade;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface EnrichmentRepository {
    Mono<ResponseEntity<Trade>> enrich(Trade trade);
}
