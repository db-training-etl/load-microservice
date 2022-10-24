package com.db.load.service;

import com.db.load.entity.Book;
import com.db.load.entity.Counterparty;
import com.db.load.repository.ReferenceDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReferenceDataService {

    ReferenceDataRepository referenceDataRepository;

    public Mono<Counterparty> saveCounterparty(Counterparty counterparty) {
        return referenceDataRepository.saveCounterparty(counterparty);
    }

    public Mono<Book> saveBook(Book book) {
        return referenceDataRepository.saveBook(book);
    }

}
