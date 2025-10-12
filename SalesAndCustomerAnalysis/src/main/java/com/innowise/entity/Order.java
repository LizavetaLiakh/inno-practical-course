package com.innowise.entity;

import com.innowise.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Order {
    private final String orderId;
    private final LocalDateTime orderDate;
    private final Customer customer;
    private final List<OrderItem> items;
    private final OrderStatus status;
}
