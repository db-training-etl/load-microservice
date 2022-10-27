package com.db.load.repository;

import com.db.load.entity.Book;
import com.db.load.entity.Counterparty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class ReferenceDataRepositoryWebClient implements ReferenceDataRepository {
    private final WebClient webClient;

    public ReferenceDataRepositoryWebClient(@Value("${reference-data-service.url}") String referenceDataServiceUrl) {
        this.webClient = WebClient.create(referenceDataServiceUrl);
    }

    //If the server is not available, the client will throw an exception
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
