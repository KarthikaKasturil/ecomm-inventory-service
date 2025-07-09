package com.secor.ecomminventoryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class MainRestController {

    private final Logger LOG = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public List<InventoryItem> getAllProducts() {
        LOG.info("getAllInventoryItems");
        return inventoryRepository.findAll();
    }

    @GetMapping("/{inventoryId}")
    public InventoryItem getInventoryForInventoryId(@PathVariable String inventoryId) {
        LOG.info("getInventoryForInventoryId({})", inventoryId);
        return inventoryRepository.findByInventoryId(inventoryId);
    }

    @GetMapping("/product/{productId}")
    public InventoryItem getInventoryForProductId(@PathVariable String productId) {
        LOG.info("getInventoryForProductId({})", productId);
        return inventoryRepository.findByProductId(productId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody InventoryItem item) {
        LOG.info("addItem({})", item);
        InventoryItem existing = inventoryRepository.findByProductId(item.getProductId());
        LOG.info("Inventory for this product exists: {}", existing != null);
        if (existing != null) {
            return ResponseEntity.status(400).body("Inventory for this product exists");
        }
        item.setLastUpdated(LocalDateTime.now());
        InventoryItem savedItem = inventoryRepository.save(item);
        return ResponseEntity.ok("Inventory for product " + savedItem.getProductId() + " created");
    }

    @PutMapping("/update/product")
    public InventoryItem updateInventory(@RequestBody InventoryItem itemDetails) {
        LOG.info("Update inventory by productId{}", itemDetails);
        InventoryItem inventory = inventoryRepository.findByProductId(itemDetails.getProductId());
        if (inventory == null) {
            LOG.error("No inventory found for productId: {}", itemDetails.getProductId());
            throw new RuntimeException("No inventory found for productId: " + itemDetails.getProductId());
        }
        inventory.setQuantity(itemDetails.getQuantity());
        inventory.setLastUpdated(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

}