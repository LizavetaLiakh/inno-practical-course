package com.innowise.service;

import com.innowise.Customer;
import com.innowise.Order;
import com.innowise.OrderItem;
import com.innowise.OrderStatus;
import java.util.*;
import java.util.stream.Collectors;

public class OrderAnalysisService {

    /**
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
        return Math.round(averageCheck * 100.0) / 100.0;
    }

    /**
     *
     * @param orders list of orders
     * @return set of customers who have more than 5 orders
     */
    public Set<Customer> getCustomersWithMoreOrders(List<Order> orders) {
        if (orders == null) {
            return Collections.emptySet();
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
