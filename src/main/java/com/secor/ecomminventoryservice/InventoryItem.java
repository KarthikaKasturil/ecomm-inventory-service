package com.secor.ecomminventoryservice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
@Getter
@Setter
public class InventoryItem
{
    @Id
    private String inventoryId;

    private String productId;
    private Integer quantity;

    private LocalDateTime lastUpdated;
}