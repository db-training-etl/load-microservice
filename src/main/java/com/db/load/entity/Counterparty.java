package com.db.load.entity;

import lombok.Data;

@Data
public class Counterparty {
    Integer bookId;
    String bookName;
    String bookAddress;
    String entity;
}