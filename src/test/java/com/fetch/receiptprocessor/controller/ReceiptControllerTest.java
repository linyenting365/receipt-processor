package com.fetch.receiptprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.service.ScoreService;
import com.fetch.receiptprocessor.service.StorageService;
import com.fetch.receiptprocessor.service.UUIDGenerateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReceiptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UUIDGenerateService uuidGenerateService;

    @MockBean
    private ScoreService scoreService;

    @MockBean
    private StorageService storageService;
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
//    void whenProcessReceipt_thenReturnsReceiptId() throws Exception {
//        String receiptJson = objectMapper.writeValueAsString(receipt);
//        when(uuidGenerateService.generateID()).thenReturn("test-id");
//
//        mockMvc.perform(post("/receipts/process")
//                        .contentType("application/json")
//                        .content(receiptJson))
//                .andExpect(status().isOk())
//                .andExpect(content().string("test-id"));
//    }
}
