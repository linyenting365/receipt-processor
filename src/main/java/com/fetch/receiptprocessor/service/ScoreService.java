package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.model.Receipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    private static final LocalTime START_TIME = LocalTime.of(14, 0);
    private static final LocalTime END_TIME = LocalTime.of(16, 0);
    private static final Integer TIME_SCORE = 10;
    private static final Integer DATE_SCORE = 6;
    private static final Integer ITEM_PAIR_SCORE = 5;

    public int processReceiptScore(Receipt receipt){
        int score = 0;

        score += getRetailerNameScore(receipt.getRetailerName());

        score += getDateScore(receipt.getPurchaseDate().getDayOfMonth());


        score += getTimeScore(receipt.getPurchaseTime());


        score += getTotalPriceScore(receipt.getTotalPrice());


        score += getItemsScore(receipt.getItems());


        return score;
    }
    public int getRetailerNameScore(String retailerName){
        int score = 0;
        for(char c : retailerName.toCharArray()){
            if(Character.isAlphabetic(c)) score++;
        }
        return score;
    }

    public int getItemsScore(List<Receipt.Item> items){
        int score = 0;
        for(Receipt.Item item : items){
            String description  = item.getShortDescription().trim();
            // calculate short description score
            score += getShortDescriptionScore(description, new BigDecimal(item.getPrice()));
        }
        // calculate number of a pair of description score
        score += (items.size() / 2) * ITEM_PAIR_SCORE;
        return score;
    }

    public int getShortDescriptionScore(String description, BigDecimal price){
        if(description.length() % 3 == 0){
            return price.multiply(new BigDecimal("0.2")).setScale(0, RoundingMode.UP).intValueExact();
        }
        return 0;
    }

    public int getTotalPriceScore(String total){
        BigDecimal totalPrice = new BigDecimal(total);
        int score = 0;
        if(totalPrice.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0){
            score += 50;
        }
        if(totalPrice.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0){
            score += 25;
        }
        return score;
    }

    public int getDateScore(int date){
        return date % 2 == 1 ? DATE_SCORE : 0;
    }

    public int getTimeScore(LocalTime time){
        return isBetweenTime(time) ? TIME_SCORE : 0;
    }

    public boolean isBetweenTime(LocalTime time){
        return !time.isBefore(START_TIME) && time.isBefore(END_TIME);
    }
}
