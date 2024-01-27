package com.fetch.receiptprocessor.controller;

import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.service.ScoreService;
import com.fetch.receiptprocessor.service.StorageService;
import com.fetch.receiptprocessor.service.UUIDGenerateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiptController {
    private final UUIDGenerateService uuidGenerateService;
    private final ScoreService scoreService;
    private final StorageService storageService;

    public ReceiptController(UUIDGenerateService uuidGenerateService, ScoreService scoreService, StorageService storageService){
        this.uuidGenerateService = uuidGenerateService;
        this.scoreService = scoreService;
        this.storageService = storageService;
    }

    @PostMapping("/receipts/process")
    public ResponseEntity<?> receiptProcess(@Valid @RequestBody Receipt receipt){
        receipt.setReceiptId(uuidGenerateService.generateID());
        storageService.putIdWithScore(receipt.getReceiptId(), scoreService.processReceiptScore(receipt));
        return ResponseEntity.ok(receipt.getReceiptId());
    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<?> getReceiptScore(@PathVariable(name = "id")String id){
        if(!storageService.isIdContains(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(storageService.getScore(id));
    }
}
