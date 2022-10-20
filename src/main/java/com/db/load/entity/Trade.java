package com.db.load.entity;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

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