package com.fetch.receiptprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.service.ScoreService;
import com.fetch.receiptprocessor.service.StorageService;
import com.fetch.receiptprocessor.service.UUIDGenerateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptController.class)
public class ReceiptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReceiptController mockReceiptController;

    @MockBean
    private UUIDGenerateService mockUuidGenerateService;

    @MockBean
    private ScoreService mockScoreService;

    @MockBean
    private StorageService mockStorageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SAMPLE_UNIQUE_ID = "sample-unique-id";
    private static final String SAMPLE_RETAILER_NAME = "sample";
    private static final String INVALID_RETAILER_NAME = "";
    private static final LocalTime SAMPLE_TIME = LocalTime.of(13, 1);
    private static final LocalDate SAMPLE_DATE = LocalDate.of(2022, 1, 1);
    private static final String SAMPLE_PRICE  = "12.25";

    private static final String INVALID_PRICE = "";
    private static  final LocalDate INVALID_DATE = null;
    private static  final LocalTime INVALID_TIME = null;
    private Receipt receipt;

    @BeforeEach
    void setUp() {
        // Create some item objects
        Receipt.Item item1 = new Receipt.Item("Item11", SAMPLE_PRICE);
        Receipt.Item item2 = new Receipt.Item("Item22", SAMPLE_PRICE);
        List<Receipt.Item> items = Arrays.asList(item1, item2);
        objectMapper.registerModule(new JavaTimeModule());
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

    @Test
    void whenProcessReceipt_withCorrectReceiptRequest_shouldReturnsReceiptId() throws Exception {
        String receiptJson = objectMapper.writeValueAsString(receipt);
        when(mockUuidGenerateService.generateID()).thenReturn("test-id");

        mockMvc.perform(post("/receipts/process")
                        .contentType("application/json")
                        .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.receiptId").value("test-id"))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    void whenProcessReceipt_withInvalidRetailerName_shouldReturnsNotFound() throws Exception{
        Receipt.Item item1 = new Receipt.Item("Item11", SAMPLE_PRICE);
        Receipt.Item item2 = new Receipt.Item("Item22", SAMPLE_PRICE);
        List<Receipt.Item> items = Arrays.asList(item1, item2);
        Receipt invalidReceipt = new Receipt(
                SAMPLE_UNIQUE_ID,
                INVALID_RETAILER_NAME,
                SAMPLE_DATE,
                SAMPLE_TIME,
                items,
                SAMPLE_PRICE
        );
        String receiptJson = objectMapper.writeValueAsString(invalidReceipt);

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProcessReceipt_withInvalidDate_shouldReturnsNotFound() throws Exception{
        Receipt.Item item1 = new Receipt.Item("Item11", SAMPLE_PRICE);
        Receipt.Item item2 = new Receipt.Item("Item22", SAMPLE_PRICE);
        List<Receipt.Item> items = Arrays.asList(item1, item2);
        Receipt invalidReceipt = new Receipt(
                SAMPLE_UNIQUE_ID,
                SAMPLE_RETAILER_NAME,
                INVALID_DATE,
                SAMPLE_TIME,
                items,
                SAMPLE_PRICE
        );
        String receiptJson = objectMapper.writeValueAsString(invalidReceipt);

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProcessReceipt_withInvalidTime_shouldReturnsNotFound() throws Exception{
        Receipt.Item item1 = new Receipt.Item("Item11", SAMPLE_PRICE);
        Receipt.Item item2 = new Receipt.Item("Item22", SAMPLE_PRICE);
        List<Receipt.Item> items = Arrays.asList(item1, item2);
        Receipt invalidReceipt = new Receipt(
                SAMPLE_UNIQUE_ID,
                SAMPLE_RETAILER_NAME,
                SAMPLE_DATE,
                INVALID_TIME,
                items,
                SAMPLE_PRICE
        );
        String receiptJson = objectMapper.writeValueAsString(invalidReceipt);

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProcessReceipt_withInvalidPrice_shouldReturnsNotFound() throws Exception{
        Receipt.Item item1 = new Receipt.Item("Item11", SAMPLE_PRICE);
        Receipt.Item item2 = new Receipt.Item("Item22", SAMPLE_PRICE);
        List<Receipt.Item> items = Arrays.asList(item1, item2);
        Receipt invalidReceipt = new Receipt(
                SAMPLE_UNIQUE_ID,
                SAMPLE_RETAILER_NAME,
                SAMPLE_DATE,
                SAMPLE_TIME,
                items,
                INVALID_PRICE
        );
        String receiptJson = objectMapper.writeValueAsString(invalidReceipt);

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPointByReceiptId_withCorrectReceiptId_shouldReturnsCorrectPoint() throws Exception{
        String validId = "12345";
        String mockPoints = "100";
        when(mockStorageService.isIdContains(validId)).thenReturn(true);
        when(mockStorageService.getScore(validId)).thenReturn(mockPoints);

        mockMvc.perform(get("/receipts/" + validId + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.points").value(mockPoints))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    public void whenProcessReceipt_withInvalidId_thenReturnsNotFound() throws Exception {
        String invalidId = "invalid-id";
        when(mockStorageService.isIdContains(invalidId)).thenReturn(false);

        mockMvc.perform(get("/receipts/" + invalidId + "/points"))
                .andExpect(status().isNotFound());
    }


}
