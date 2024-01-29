package com.fetch.receiptprocessor.controller;

import com.fetch.receiptprocessor.model.ApiResponse;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.model.ReceiptIdResponse;
import com.fetch.receiptprocessor.model.ReceiptPointsResponse;
import com.fetch.receiptprocessor.service.ScoreService;
import com.fetch.receiptprocessor.service.StorageService;
import com.fetch.receiptprocessor.service.UUIDGenerateService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiptController {
    private final UUIDGenerateService uuidGenerateService;
    private final ScoreService scoreService;
    private final StorageService storageService;

    private static final Logger logger = LogManager.getLogger(ScoreService.class);

    public ReceiptController(UUIDGenerateService uuidGenerateService, ScoreService scoreService, StorageService storageService){
        this.uuidGenerateService = uuidGenerateService;
        this.scoreService = scoreService;
        this.storageService = storageService;
    }

    @PostMapping("/receipts/process")
    public ResponseEntity<?> receiptProcess(@Valid @RequestBody Receipt receipt){
        String receiptId = uuidGenerateService.generateID();

        receipt.setReceiptId(receiptId);
        storageService.putIdWithScore(receipt.getReceiptId(), scoreService.processReceiptScore(receipt));
        ReceiptIdResponse response = ReceiptIdResponse.builder().receiptId(receiptId).build();
        logger.info("Receipt id: {}", receiptId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response));
    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<?> getReceiptScore(@PathVariable(name = "id")String id){
        if(!storageService.isIdContains(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null));
        }
        String points  = storageService.getScore(id);
        ReceiptPointsResponse response = ReceiptPointsResponse.builder().points(points).build();
        logger.info("Receipt id: {}, points: {}", id, points);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response));
    }
}
