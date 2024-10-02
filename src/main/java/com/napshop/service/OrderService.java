package com.napshop.service;

import com.napshop.dto.OrderRequest;
import com.napshop.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

    List<OrderResponse> getOrders();
}
