package com.fetch.receiptprocessor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor  // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class Receipt {
    private String receiptId;

    @NotBlank
    @Pattern(regexp = "\\S.*\\S$")
    @JsonProperty("retailer")
    private String retailerName;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime purchaseTime;

    @NotEmpty
    @JsonProperty("items")
    private List<Item> items;

    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    @JsonProperty("total")
    private String totalPrice;

    @Data
    @NoArgsConstructor  // Generates a no-argument constructor
    @AllArgsConstructor // Generates a constructor with all fields
    public static class Item {
        @NotBlank
        @Pattern(regexp = "^[\\w\\s\\-]+$")
        @JsonProperty("shortDescription")
        private String shortDescription;

        @NotBlank
        @Pattern(regexp = "^\\d+\\.\\d{2}$")
        @JsonProperty("price")
        private String price;
    }
}
