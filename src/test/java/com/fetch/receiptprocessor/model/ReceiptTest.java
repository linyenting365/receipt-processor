package com.fetch.receiptprocessor.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SAMPLE_UNIQUE_ID = "sample-unique-id";
    private static final String SAMPLE_RETAILER_NAME = "sample";
    private static final LocalTime SAMPLE_TIME = LocalTime.of(13, 1);
    private static final LocalDate SAMPLE_DATE = LocalDate.of(2022, 1, 1);
    private static final String SAMPLE_PRICE  = "12.25";
    private Receipt receipt;

    @BeforeEach
    void setUp() {
        // Create some item objects
        Receipt.Item item1 = new Receipt.Item("Item11", SAMPLE_PRICE);
        Receipt.Item item2 = new Receipt.Item("Item22", SAMPLE_PRICE);
        List<Receipt.Item> items = Arrays.asList(item1, item2);

        // Create a Receipt object with test data
        receipt = new Receipt(
                SAMPLE_UNIQUE_ID,
                SAMPLE_RETAILER_NAME,
                SAMPLE_DATE,
                SAMPLE_TIME,
                items,
                SAMPLE_PRICE
        );
    }

//    @Test
//    void givenReceiptObject_whenSerializingDeserializing_thenCorrect() throws Exception {
//
//        // Serialize
//        String jsonString = objectMapper.writeValueAsString(receipt);
//
//        // Deserialize
//        Receipt deserializedReceipt = objectMapper.readValue(jsonString, Receipt.class);
//
//        assertEquals(receipt, deserializedReceipt);
//    }
}
