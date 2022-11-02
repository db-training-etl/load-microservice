package com.db.load.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Counterparty {
    Integer counterpartyId;
    String counterpartyName;
    String source;
    String entity;
}