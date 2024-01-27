package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreServiceTest {
    private ScoreService scoreService;
    private List<Receipt.Item> testItems;

    @BeforeEach
    void setUp() {
        scoreService = new ScoreService();
        testItems = Arrays.asList(
                new Receipt.Item("Item11", "12.45"),
                new Receipt.Item("Item22", "12.4")
        );
    }

    @Test
    void getRetailerNameScore_withValidName_shouldReturnCorrectScore(){
        String retailerName = "Target";
        int expectedScore = 6;
        assertEquals(expectedScore, scoreService.getRetailerNameScore(retailerName));
    }

    @Test
    void getTotalPriceScore_withRoundAmount_shouldReturnCorrectScore() {
        String total = "100.00";
        int expectedScore = 75;
        assertEquals(expectedScore, scoreService.getTotalPriceScore(total));
    }

    @Test
    void getTotalPriceScore_withMultipleO25_shouldReturnCorrectScore() {
        String total = "1.25";
        int expectedScore = 25;
        assertEquals(expectedScore, scoreService.getTotalPriceScore(total));
    }

    @Test
    void getTotalPriceScore_withDecimals_shouldNotReturnScore() {
        String total = "1.15";
        int expectedScore = 0;
        assertEquals(expectedScore, scoreService.getTotalPriceScore(total));
    }

    @Test
    void getItemsScore_withValidItems_shouldReturnCorrectScore() {
        int expectedScore = 11;
        assertEquals(expectedScore, scoreService.getItemsScore(testItems));
    }

    @Test
    void getItemsScore_withEmptyList_shouldReturnZero() {
        List<Receipt.Item> emptyItems = Collections.emptyList();
        assertEquals(0, scoreService.getItemsScore(emptyItems));
    }

    @Test
    void getTimeScore_BeforeStartTime_ShouldReturnZero() {
        LocalTime time = LocalTime.of(13, 59); // One minute before START_TIME
        assertEquals(0, scoreService.getTimeScore(time));
    }

    @Test
    void getTimeScore_DuringTimeRange_ShouldReturnTimeScore() {
        LocalTime time = LocalTime.of(14, 30); // Within the range
        assertEquals(10, scoreService.getTimeScore(time));
    }

    @Test
    void getTimeScore_AfterEndTime_ShouldReturnZero() {
        LocalTime time = LocalTime.of(16, 01); // One minute after END_TIME
        assertEquals(0, scoreService.getTimeScore(time));
    }

}
