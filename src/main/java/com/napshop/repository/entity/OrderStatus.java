package com.napshop.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("Pending") PENDING,
    @JsonProperty("Shipped") SHIPPED,
    @JsonProperty("Delivered") DELIVERED,
    @JsonProperty("Cancelled") CANCELLED
}
