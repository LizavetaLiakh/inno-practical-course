package com.innowise.analyseservice;

import com.innowise.Customer;
import com.innowise.Order;
import com.innowise.OrderItem;
import com.innowise.OrderStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class OrderAnalysisService {

    private static final int MIN_ORDER_AMOUNT = 5;

    /**
     * Collects all unique cities where clients live.
     *
     * @param orders list of orders
     * @return set of unique cities where customers live
     */
    public Set<String> getUniqueCities(List<Order> orders) {
        if (orders == null) {
            return Set.of();
        }
        return orders.stream()
                .filter(Objects::nonNull)
                .map(Order::getCustomer)
                .filter(Objects::nonNull)
                .map(Customer::getCity)
                .filter(city -> city != null && !city.isBlank())
                .collect(Collectors.toSet());
    }

    /**
     * Calculates total income from completed orders.
     *
     * @param orders list of orders
     * @return total income for all completed orders
     */
    public double getTotalIncome(List<Order> orders) {
        if (orders == null) {
            return 0.0;
        }
        return orders.stream()
                .filter(order -> order != null && order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .filter(Objects::nonNull)
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    /**
     * Finds the most popular product among customers' orders.
     *
     * @param orders list of orders
     * @return the most popular product by sales
     */
    public Optional<String> getMostPopularProduct(List<Order> orders) {
        if (orders == null) {
            return Optional.empty();
        }
        return orders.stream()
                .filter(Objects::nonNull)
                .flatMap(order -> Optional.ofNullable(order.getItems()).orElse(List.of()).stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    /**
     * Calculates average check from all successfully delivered orders.
     *
     * @param orders list of orders
     * @return average check for successfully delivered orders
     */
    public double getAverageCheck(List<Order> orders) {
        if (orders == null) {
            return 0;
        }
        double averageCheck =  orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> Optional.ofNullable(order.getItems()).orElse(List.of()).stream()
                        .filter(Objects::nonNull)
                        .mapToDouble(item -> item.getQuantity() * item.getPrice()).sum())
                .average()
                .orElse(0);
        return BigDecimal.valueOf(averageCheck).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Finds customers who have more than 5 orders.
     *
     * @param orders list of orders
     * @return set of customers who have more than 5 orders
     */
    public Set<Customer> getCustomersWithMoreOrders(List<Order> orders) {
        if (orders == null) {
            return Collections.emptySet();
        }
        return orders.stream()
                .filter(order -> order != null && order.getCustomer() != null)
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > MIN_ORDER_AMOUNT)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
