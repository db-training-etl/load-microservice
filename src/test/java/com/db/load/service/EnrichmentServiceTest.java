package com.db.load.service;

import com.db.load.entity.Trade;
import com.db.load.repository.EnrichmentRepositoryWebClient;
import com.db.load.repository.ReferenceDataRepositoryWebClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnrichmentServiceTest {

    EnrichmentRepositoryWebClient enrichmentRepositoryWebClient;
    EnrichmentService enrichmentService;

    MockWebServer mockEnrichmentService;

    @BeforeEach
    void setUp() {
        mockEnrichmentService = new MockWebServer();
        enrichmentRepositoryWebClient = new EnrichmentRepositoryWebClient(mockEnrichmentService.url("/").toString());
        enrichmentService = new EnrichmentService(enrichmentRepositoryWebClient);
    }

    @Test
    void given_two_correct_trades_when_enrich_then_successList_is_length_two() {
        //WHEN
        mockEnrichmentService.enqueue(new MockResponse()
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
        mockEnrichmentService.enqueue(new MockResponse()
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
        StepVerifier.create(enrichmentService.enrichTrades(List.of(new Trade(), new Trade())))
                .expectNextMatches(enrichedTrades -> enrichedTrades.getSuccessCount() == 2)
                .verifyComplete();
    }

    @Test
    void given_wrong_trades_when_enrich_then_successList_is_length_zero() {
        //WHEN
        mockEnrichmentService.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(400));
        mockEnrichmentService.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(400));
        //THEN
        StepVerifier.create(enrichmentService.enrichTrades(List.of(new Trade(), new Trade())))
                .expectNextMatches(enrichedTrades -> enrichedTrades.getSuccessCount() == 0 && enrichedTrades.getFailedCount() == 2)
                .verifyComplete();
    }

    @Test
    void given_one_correct_and_one_wrong_trades_when_enrich_then_successList_is_length_one() {
        //WHEN
        mockEnrichmentService.enqueue(new MockResponse()
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
        mockEnrichmentService.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(400));
        //THEN
        StepVerifier.create(enrichmentService.enrichTrades(List.of(new Trade(), new Trade())))
                .expectNextMatches(enrichedTrades -> enrichedTrades.getSuccessCount() == 1 && enrichedTrades.getFailedCount() == 1)
                .verifyComplete();
    }
}