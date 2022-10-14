package com.db.load.Entity;

import lombok.Data;

@Data
public class Counterparty {
    Integer bookId;
    String bookName;
    String bookAddress;
    String entity;
}