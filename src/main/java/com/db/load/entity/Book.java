package com.db.load.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    Integer bookId;
    String bookName;
    String bookAddress;
    String entity;
}