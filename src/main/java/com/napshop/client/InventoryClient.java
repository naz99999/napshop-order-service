package com.napshop.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {

    @GetMapping("/api/inventory")
    boolean checkInventory(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
}
