package com.db.load.Entity;

import lombok.Data;

import java.util.Date;

@Data
public class Trade {
    Integer id;
    String tradeName;
    Integer bookId;
    String country;
    Integer counterpartyId;
    String currency;
    Date cobDate;
    Double amount;
    Boolean tradeTax;
}