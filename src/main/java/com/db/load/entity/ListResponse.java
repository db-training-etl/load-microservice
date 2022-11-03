package com.db.load.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ListResponse<T> {
    String message;
    Integer successCount;
    List<T> successList;
    Integer failedCount;
}
