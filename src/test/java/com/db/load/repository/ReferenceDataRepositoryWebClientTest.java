package com.db.load.repository;

import com.db.load.entity.Counterparty;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientRequestException;
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
        StepVerifier.create(referenceDataRepositoryWebClient.saveCounterparty(new Counterparty()))
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
    void saveCounterparty_ServerNoReachable() throws IOException {
        //WHEN
        mockReferenceDataServer.shutdown();
        //THEN
        StepVerifier.create(this.referenceDataRepositoryWebClient.saveCounterparty(new Counterparty()))
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
        StepVerifier.create(referenceDataRepositoryWebClient.saveBook(new com.db.load.entity.Book()))
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
    void saveBook_ServerNoReachable() throws IOException {
        //WHEN
        mockReferenceDataServer.shutdown();
        //THEN
        StepVerifier.create(this.referenceDataRepositoryWebClient.saveBook(new com.db.load.entity.Book()))
                .expectError(WebClientRequestException.class)
                .verify();
    }
}