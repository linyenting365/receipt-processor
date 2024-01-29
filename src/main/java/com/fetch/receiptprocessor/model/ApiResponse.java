package com.fetch.receiptprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T>{
    private int statusCode;
    private T data;
}
