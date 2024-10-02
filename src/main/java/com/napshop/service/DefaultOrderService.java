package com.napshop.service;

import com.napshop.client.InventoryClient;
import com.napshop.dto.OrderRequest;
import com.napshop.dto.OrderResponse;
import com.napshop.exception.OutOfStockException;
import com.napshop.repository.OrderRepository;
import com.napshop.repository.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DefaultOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public DefaultOrderService(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        boolean isInStock = inventoryClient.checkInventory(orderRequest.skuCode(), orderRequest.quantity());

        if (!isInStock) {
            throw new OutOfStockException("Product with SKU " + orderRequest.skuCode() + " is out of stock");
        }

        Order order = new Order();
        LocalDate orderDate = getLocalDate(orderRequest);

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        order.setOrderDate(orderDate);

        orderRepository.save(order);
        log.info("Order {} created", order.getSkuCode());

        return new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(),
                order.getPrice(), order.getQuantity(), order.getOrderDate());
    }

    @Override
    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order ->
                        new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity(), order.getOrderDate()))
                .toList();
    }

    private static LocalDate getLocalDate(OrderRequest orderRequest) {
        String orderDateString = orderRequest.orderDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate orderDate = null;
        
        try {
            orderDate = LocalDate.parse(orderDateString, formatter);
        }  catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return orderDate;
    }
}
