package com.OrderConsumerApplication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class InventoryManagement {

    private final Map<String, Integer> productStockMap;
    private static final Logger logger = LoggerFactory.getLogger(InventoryManagement.class);

    public InventoryManagement(){
        this.productStockMap = new ConcurrentHashMap<>();
        this.productStockMap.put("Guitar", 3);
        this.productStockMap.put("Amplifier", 2);
        this.productStockMap.put("Microphone", 0);
    }

    public boolean productIsInStock(String product){
        boolean inStock = productStockMap.getOrDefault(product, 0) > 0;
        if (!inStock){logger.debug("Product {} not in stock or is unknown", product);}
        return inStock;
    }

    public void takeProductFromInventory(String product){
        if (productIsInStock(product)){
            this.productStockMap.put(product, productStockMap.get(product) - 1);
            logger.info("Inventory updated: {} remaining for {}", productStockMap.getOrDefault(product, 0), product);
        }
    }
}
