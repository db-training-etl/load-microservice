package com.db.load.repository;

import com.db.load.entity.Book;
import com.db.load.entity.Counterparty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ReferenceDataRepositoryWebClient implements ReferenceDataRepository {

    @Value("${reference-data-service.url}")
    private String referenceDataServiceUrl;

    private final WebClient webClient = WebClient.create(referenceDataServiceUrl);

    @Override
    public Mono<Counterparty> saveCounterparty(Counterparty counterparty) {
        return webClient.put()
                .uri("/counterparty")
                .bodyValue(counterparty)
                .retrieve()
                .bodyToMono(Counterparty.class);
    }

    @Override
    public Mono<Book> saveBook(Book book) {
        return webClient.put()
                .uri("/book")
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class);
    }
}
