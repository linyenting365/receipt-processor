package com.fetch.receiptprocessor.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerateService {
    public String generateID(){
        return UUID.randomUUID().toString();
    }
}
