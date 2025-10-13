package com.innowise.entity;

import com.innowise.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItem {
    private final String productName;
    private final int quantity;
    private final double price;
    private final Category category;
}
