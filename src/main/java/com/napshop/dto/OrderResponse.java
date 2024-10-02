package com.napshop.dto;

import com.napshop.repository.entity.Order;
import com.napshop.repository.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderResponse(
        Long id,
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity,
        LocalDate orderDate
) {
}
