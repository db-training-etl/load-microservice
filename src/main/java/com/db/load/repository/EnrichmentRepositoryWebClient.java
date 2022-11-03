package com.db.load.repository;

import com.db.load.entity.Trade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class EnrichmentRepositoryWebClient implements EnrichmentRepository {

    private final WebClient webClient;

    public EnrichmentRepositoryWebClient(@Value("${enrichment-service.url}") String enrichmentServiceUrl) {
        this.webClient = WebClient.create(enrichmentServiceUrl);
    }


    @Override
    public Mono<ResponseEntity<Trade>> enrich(Trade trade) {
        return webClient.post()
                .uri("/trades/enrich")
                .bodyValue(trade)
                .retrieve()
                .toEntity(Trade.class)
                .onErrorResume(throwable -> Mono.just(ResponseEntity.badRequest().body(trade)));
    }
}
