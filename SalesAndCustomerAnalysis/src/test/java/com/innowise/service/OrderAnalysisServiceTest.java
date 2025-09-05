package com.innowise.service;

import com.innowise.Category;
import com.innowise.Customer;
import com.innowise.Order;
import com.innowise.OrderItem;
import com.innowise.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Order Analysis Service Tests")
class OrderAnalysisServiceTest {

    private OrderAnalysisService service;
    private List<Order> orders;
    private List<Customer> customers;
    private List<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        service = new OrderAnalysisService();
        initializeTestData();
    }

    private void initializeTestData() {
        orderItems = Arrays.asList(
                createOrderItem("Red T-shirt", 1, 24.99, Category.CLOTHING),
                createOrderItem("White socks", 3, 3.55, Category.CLOTHING),
                createOrderItem("Y-89812 Wired Mouse", 1, 62.99, Category.ELECTRONICS),
                createOrderItem("Wooden rainbow", 1, 15.88, Category.TOYS),
                createOrderItem("Toothpaste", 1, 15.88, Category.BEAUTY),
                createOrderItem("I-Phone 16 Pro MAX", 1, 24.99, Category.ELECTRONICS),
                createOrderItem("Harry Potter", 4, 10.95, Category.BOOKS),
                createOrderItem("Pink skirt", 1, 19.67, Category.CLOTHING),
                createOrderItem("King-size bed (White)", 1, 290.50, Category.HOME),
                createOrderItem("Kitchen table", 1, 185.25, Category.HOME),
                createOrderItem("Big lamp", 8, 33.90, Category.HOME),
                createOrderItem("Pink lipstick", 2, 8.56, Category.BEAUTY)
        );

        customers = Arrays.asList(
                createCustomer("109883", "Patrick", "pp333@gmail.com", 31, "Denver"),
                createCustomer("260088", "Lily", "lily2802@gmail.com", 20, "Denver"),
                createCustomer("540117", "Paul", "hiipp111@gmail.com", 29, "Los Angeles")
        );

        orders = Arrays.asList(
                createOrder("10099100", customers.get(0), OrderStatus.DELIVERED,
                        Arrays.asList(orderItems.get(0), orderItems.get(1))),
                createOrder("10099101", customers.get(1), OrderStatus.CANCELLED,
                        Arrays.asList(orderItems.get(2))),
                createOrder("10099102", customers.get(2), OrderStatus.PROCESSING,
                        Arrays.asList(orderItems.get(3), orderItems.get(4))),
                createOrder("10099103", customers.get(2), OrderStatus.DELIVERED,
                        Arrays.asList(orderItems.get(5))),
                createOrder("10099104", customers.get(2), OrderStatus.DELIVERED,
                        Arrays.asList(orderItems.get(6))),
                createOrder("10099105", customers.get(2), OrderStatus.DELIVERED,
                        Arrays.asList(orderItems.get(7))),
                createOrder("10099106", customers.get(2), OrderStatus.DELIVERED,
                        Arrays.asList(orderItems.get(8), orderItems.get(9))),
                createOrder("10099107", customers.get(2), OrderStatus.DELIVERED,
                        Arrays.asList(orderItems.get(10))),
                createOrder("10099108", customers.get(0), OrderStatus.SHIPPED,
                        Arrays.asList(orderItems.get(11)))
        );
    }

    private OrderItem createOrderItem(String productName, int quantity, double price, Category category) {
        OrderItem item = new OrderItem(productName, quantity, price, category);
        return item;
    }

    private Customer createCustomer(String id, String name, String email, int age, String city) {
        Customer customer = new Customer(id, name, email, LocalDateTime.now(), age, city);
        return customer;
    }

    private Order createOrder(String id, Customer customer, OrderStatus status, List<OrderItem> items) {
        Order order = new Order(id, LocalDateTime.now(), customer, items, status);
        return order;
    }

    @Test
    @DisplayName("Get unique cities from orders")
    void testGetUniqueCities() {
        Set<String> result = service.getUniqueCities(orders);

        assertEquals(2, result.size());
        assertTrue(result.contains("Denver"));
        assertTrue(result.contains("Los Angeles"));
        assertFalse(result.contains("New York"));
    }

    @Test
    @DisplayName("Get total income from delivered orders")
    void testGetTotalIncome() {
        double result = service.getTotalIncome(orders);

        assertEquals(871.05, result, 0.01, "The total income must be 888.17");
    }

    @Test
    @DisplayName("Get average check for delivered orders")
    void testGetAverageCheck() {
        double result = service.getAverageCheck(orders);

        assertEquals(145.17, result, 0.01, "The average check must be 126.88");
    }

    @Test
    @DisplayName("Get most popular product")
    void testGetMostPopularProduct() {
        Optional<String> result = service.getMostPopularProduct(orders);

        assertTrue(result.isPresent());
        assertEquals("Big lamp", result.get());
    }

    @Test
    @DisplayName("Get customers with more than 5 orders")
    void testGetCustomersWithMoreThan5Orders() {
        Set<Customer> result = service.getCustomersWithMoreOrders(orders);

        assertEquals(1, result.size());
        assertTrue(result.contains(customers.get(2)));
    }

    // Edge case tests
    @Test
    @DisplayName("Get unique cities from empty orders list")
    void testGetUniqueCitiesWithEmptyList() {
        Set<String> result = service.getUniqueCities(Collections.emptyList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Get total income from null orders list")
    void testGetTotalIncomeNull() {
        double result = service.getTotalIncome(null);

        assertEquals(0.0, result, 0.01);
    }

    @Test
    @DisplayName("Get most popular product with no orders")
    void testGetMostPopularProductWithEmptyList() {
        Optional<String> result = service.getMostPopularProduct(Collections.emptyList());

        assertFalse(result.isPresent());
    }

    @ParameterizedTest
    @MethodSource("orderStatusProvider")
    @DisplayName("Get total income with different order statuses")
    void testGetTotalIncomeWithDifferentStatuses(OrderStatus status, double expectedIncome) {
        Order testOrder = createOrder("test001", customers.get(0), status,
                Arrays.asList(createOrderItem("Test Product", 2, 10.0, Category.ELECTRONICS)));

        double result = service.getTotalIncome(Collections.singletonList(testOrder));

        assertEquals(expectedIncome, result, 0.01);
    }

    private static Stream<Arguments> orderStatusProvider() {
        return Stream.of(
                arguments(OrderStatus.DELIVERED, 20.0),
                arguments(OrderStatus.PROCESSING, 0.0),
                arguments(OrderStatus.SHIPPED, 0.0),
                arguments(OrderStatus.CANCELLED, 0.0),
                arguments(OrderStatus.NEW, 0.0)
        );
    }

    @Test
    @DisplayName("Get unique cities with null customer city")
    void testGetUniqueCitiesWithNullCity() {
        Customer customerWithNullCity = createCustomer("test123", "Test", "test@test. com"
                , 25, null);
        Order order = createOrder("test002", customerWithNullCity, OrderStatus.DELIVERED,
                Arrays.asList(createOrderItem("Test Item", 1, 15.0, Category.BOOKS)));

        Set<String> result = service.getUniqueCities(Collections.singletonList(order));

        assertTrue(result.isEmpty());
    }
}