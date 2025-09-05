package org.example.service;

import org.example.Customer;
import org.example.Order;
import org.example.OrderItem;
import org.example.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

public class OrderAnalysisService {

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

    public Optional<String> getMostPopularProduct(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        return orders.stream()
                .filter(Objects::nonNull)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public double getAverageCheck(List<Order> orders) {
        if (orders == null) {
            return 0;
        }
        double averageCheck =  orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getPrice()).sum())
                .average()
                .orElse(0);
        return Math.round(averageCheck * 100.0) / 100.0;
    }

    public Set<Customer> getCustomersWithMoreOrders(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        Set<String> customerIds = orders.stream()
                .filter(order -> order != null && order.getCustomer() != null)
                .collect(Collectors.groupingBy(order -> order.getCustomer().getCustomerId(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return orders.stream()
                .map(Order::getCustomer)
                .filter(Objects::nonNull)
                .filter(customer -> customerIds.contains(customer.getCustomerId()))
                .collect(Collectors.toSet());
    }

}
