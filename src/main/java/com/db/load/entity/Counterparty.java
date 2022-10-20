package com.db.load.entity;

import lombok.Data;

@Data
public class Counterparty {
    Integer counterpartyId;
    String counterpartyName;
    String source;
    String entity;
}