package com.fetch.receiptprocessor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReceiptIdResponse {
    private String receiptId;
}
