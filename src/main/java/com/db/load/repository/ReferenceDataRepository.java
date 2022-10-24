package com.db.load.repository;

import com.db.load.entity.Book;
import com.db.load.entity.Counterparty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface ReferenceDataRepository{
    Mono<Counterparty> saveCounterparty(Counterparty counterparty);
    Mono<Book> saveBook(Book book);
}
