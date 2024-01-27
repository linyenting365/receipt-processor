package com.fetch.receiptprocessor.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class StorageService {
    Map<String, Integer> idScoreMap = new HashMap<>();
    public Boolean isIdContains(String id){
        return idScoreMap.containsKey(id);
    }

    public void putIdWithScore(String id, int score){
         idScoreMap.put(id, score);
    }

    public Integer getScore(String id){
        return idScoreMap.get(id);
    }
}
