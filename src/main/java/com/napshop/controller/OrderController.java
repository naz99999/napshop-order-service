package com.napshop.controller;

import com.napshop.dto.OrderRequest;
import com.napshop.dto.OrderResponse;
import com.napshop.exception.OutOfStockException;
import com.napshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.napshop.exception.model.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService OrderService) {
        this.orderService = OrderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Order request {}", orderRequest);
        try {
            OrderResponse response = orderService.createOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (OutOfStockException e) {
            ErrorResponse errorResponse = new ErrorResponse("OUT_OF_STOCK", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getProducts() {
        return orderService.getOrders();
    }

}

