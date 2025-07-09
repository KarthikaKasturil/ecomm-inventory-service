package com.secor.ecomminventoryservice;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<InventoryItem, String>
{
    InventoryItem findByInventoryId(String inventoryId);
    InventoryItem findByProductId(String productId);
}
