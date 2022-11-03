package com.db.load.repository;

import com.db.load.entity.Trade;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnrichmentRepositoryWebClientTest {

    MockWebServer mockEnrichmentServer;

    EnrichmentRepositoryWebClient enrichmentRepositoryWebClient;

    @BeforeEach
    void setUp() {
        mockEnrichmentServer = new MockWebServer();
        enrichmentRepositoryWebClient = new EnrichmentRepositoryWebClient(mockEnrichmentServer.url("/").toString());
    }

    @Test
    void given_correct_tradeList_when_enrich_then_return_enriched_trade() {
    //WHEN
        mockEnrichmentServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody("""
                          {
                            "id": 1,
                            "tradeName": "hola",
                            "bookId": 1,
                            "book": {
                              "bookId": 1,
                              "bookName": "hola",
                              "source": "hola",
                              "entity": "hola"
                            },
                            "country": "CA",
                            "counterpartyId": 2,
                            "counterparty": {
                              "counterpartyId": 2,
                              "counterpartyName": "hola",
                              "source": "hola",
                              "entity": "hola"
                            },
                            "currency": "USD",
                            "cobDate": "2022-10-10",
                            "amount": 10.0,
                            "tradeTax": false
                          }
                        """));
        //THEN
        StepVerifier.create(enrichmentRepositoryWebClient.enrich(new Trade()))
                .expectNextMatches(enrichedTradeResponse -> enrichedTradeResponse.getStatusCode() == HttpStatus.OK)
                .verifyComplete();
    }

    @Test
    void given_incorrect_tradeList_when_enrich_then_return_bad_request() {
        //WHEN
        mockEnrichmentServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(400)
                .setBody("""
                        {
                          "timestamp": "2021-10-10T10:10:10.000+00:00",
                          "status": 400,
                          "error": "Bad Request",
                          "message": "Validation failed for argument [0] in public org.springframework.http.ResponseEntity<java.util.List<com.db.load.entity.Trade>> com.db.load.controller.EnrichmentController.enrich(java.util.List<com.db.load.entity.Trade>): [Field error in object 'trade' on field 'id': rejected value [null]; codes [NotNull.trade.id,NotNull.id,NotNull.java.lang.Integer,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [trade.id,id]; arguments []; default message [id]]; default message [must not be null]] ",
                          "path": "/enrich"
                        }
                        """));
        //THEN
        StepVerifier.create(enrichmentRepositoryWebClient.enrich(new Trade()))
                .expectNextMatches(enrichedTradeResponse -> enrichedTradeResponse.getStatusCode() == HttpStatus.BAD_REQUEST)
                .verifyComplete();
    }

    @Test
    void given_incorrect_tradeList_when_enrich_and_server_not_available_then_request_exception() throws IOException {
        //WHEN
        mockEnrichmentServer.shutdown();
        //THEN
        StepVerifier.create(enrichmentRepositoryWebClient.enrich(new Trade()))
                .expectError(WebClientRequestException.class)
                .verify();
    }
}