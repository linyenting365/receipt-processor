package com.fetch.receiptprocessor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageServiceTest {

    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageService();
    }

    @Test
    void putIdWithScore_And_GetScore_ShouldReturnCorrectScore() {
        String testId = "testId";
        int testScore = 100;

        storageService.putIdWithScore(testId, testScore);

        assertEquals(testScore, storageService.getScore(testId));
    }

    @Test
    void isIdContains_WithExistingId_ShouldReturnTrue() {
        String testId = "existingId";
        storageService.putIdWithScore(testId, 50);

        assertTrue(storageService.isIdContains(testId));
    }

    @Test
    void isIdContains_WithNonExistingId_ShouldReturnFalse() {
        String testId = "nonExistingId";

        assertFalse(storageService.isIdContains(testId));
    }

    @Test
    void getScore_WithNonExistingId_ShouldReturnNull() {
        String testId = "nonExistingId";

        assertNull(storageService.getScore(testId));
    }
}