package com.napshop.dto;

import com.napshop.repository.OrderRepository;
import com.napshop.repository.entity.Order;
import com.napshop.repository.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderRequest(
        Long id,
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity,
        String orderDate
) {
}
