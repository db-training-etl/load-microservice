package com.db.load.repository;

import com.db.load.entity.Book;
import com.db.load.entity.Counterparty;
import reactor.core.publisher.Mono;

public interface ReferenceDataRepository{
    Mono<Counterparty> saveCounterparty(Counterparty counterparty);
    Mono<Book> saveBook(Book book);
}
