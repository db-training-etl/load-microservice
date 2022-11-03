package com.db.load.repository;

import com.db.load.entity.Book;
import com.db.load.entity.Counterparty;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import java.io.IOException;

class ReferenceDataRepositoryWebClientTest {

    MockWebServer mockReferenceDataServer;
    ReferenceDataRepositoryWebClient referenceDataRepositoryWebClient;

    @BeforeEach
    void setUp() {
        //GIVEN
        mockReferenceDataServer = new MockWebServer();
        referenceDataRepositoryWebClient = new ReferenceDataRepositoryWebClient(mockReferenceDataServer.url("/").toString());
    }

    @Test
    void saveCounterparty_Ok() {
        //WHEN
        mockReferenceDataServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody("""
                        {
                          "counterpartyId": 0,
                          "counterpartyName": "string",
                          "source": "string",
                          "entity": "string"
                        }
                        """));
        //THEN
        StepVerifier.create(referenceDataRepositoryWebClient.saveCounterparty(new Counterparty(0, "string", "string", "string")))
                .expectNextMatches(
                        counterparty ->
                                counterparty.getCounterpartyId() == 0
                                        && counterparty.getCounterpartyName().equals("string")
                                        && counterparty.getSource().equals("string")
                                        && counterparty.getEntity().equals("string")
                )
                .verifyComplete();
    }

    @Test
    void saveCounterparty_BadRequest(){
        //WHEN
        mockReferenceDataServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(400)
                .setBody("""
                        {
                          "timestamp": "2021-07-07T12:00:00.000+00:00",
                          "status": 400,
                          "error": "Bad Request",
                          "message": "Validation failed for argument [0] in public reactor.core.publisher.Mono<com.db.load.entity.Counterparty> com.db.load.repository.ReferenceDataRepositoryWebClient.saveCounterparty(com.db.load.entity.Counterparty): [Field error in object 'counterparty' on field 'counterpartyName': rejected value [null]; codes [NotNull.counterparty.counterpartyName,NotNull.counterpartyName,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [counterparty.counterpartyName,counterpartyName]; arguments []; default message [counterpartyName]]; default message [must not be null]] ",
                          "path": "/counterparties"
                        }
                        """));
        //THEN
        StepVerifier.create(referenceDataRepositoryWebClient.saveCounterparty(new Counterparty()))
                .expectError(WebClientResponseException.BadRequest.class)
                .verify();
    }

    @Test
    void saveCounterparty_ServerNoReachable() throws IOException {
        //WHEN
        mockReferenceDataServer.shutdown();
        //THEN
        StepVerifier.create(this.referenceDataRepositoryWebClient.saveCounterparty(new Counterparty(0, "string", "string", "string")))
                .expectError(WebClientRequestException.class)
                .verify();
    }

    @Test
    void saveBook_Ok() {
        //WHEN
        mockReferenceDataServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody("""
                        {
                          "bookId": 0,
                          "bookName": "string",
                          "bookAddress": "string",
                          "entity": "string"
                        }
                        """));
        //THEN
        StepVerifier.create(referenceDataRepositoryWebClient.saveBook(new Book(1, "string", "string", "string")))
                .expectNextMatches(
                        book ->
                                book.getBookId() == 0
                                        && book.getBookName().equals("string")
                                        && book.getBookAddress().equals("string")
                                        && book.getEntity().equals("string")
                )
                .verifyComplete();
    }

    @Test
    void saveBook_BadRequest(){
        //WHEN
        mockReferenceDataServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(400)
                .setBody("""
                        {
                          "timestamp": "2021-07-07T12:00:00.000+00:00",
                          "status": 400,
                          "error": "Bad Request",
                          "message": "Validation failed for argument [0] in public reactor.core.publisher.Mono<com.db.load.entity.Book> com.db.load.repository.ReferenceDataRepositoryWebClient.saveBook(com.db.load.entity.Book): [Field error in object 'book' on field 'bookName': rejected value [null]; codes [NotNull.book.bookName,NotNull.bookName,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [book.bookName,bookName]; arguments []; default message [bookName]]; default message [must not be null]] ",
                          "path": "/books"
                        }
                        """));
        //THEN
        StepVerifier.create(referenceDataRepositoryWebClient.saveBook(new Book()))
                .expectErrorMatches(throwable -> throwable instanceof WebClientResponseException)
                .verify();
    }

    @Test
    void saveBook_ServerNoReachable() throws IOException {
        //WHEN
        mockReferenceDataServer.shutdown();
        //THEN
        StepVerifier.create(this.referenceDataRepositoryWebClient.saveBook(new Book(1, "string", "string", "string")))
                .expectError(WebClientRequestException.class)
                .verify();
    }
}