package com.noobsmoke;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;

public class OrderService {
    private final PaymentProcessor paymentProcessor;
    private final OrderRepository orderRepository;
    private final Random random;

    public OrderService(PaymentProcessor paymentProcessor, OrderRepository orderRepository) {
        this.paymentProcessor = paymentProcessor;
        this.orderRepository = orderRepository;
        this.random = new Random();
    }

    public boolean processOrder(User user, BigDecimal amount) {
        boolean isCharged = paymentProcessor.charge(amount);
        if (!isCharged) {
            throw new IllegalStateException("Payment Failed");
        }

        Order order = new Order(
                random.nextInt(10001),
                user,
                amount,
                ZonedDateTime.now()
        );
        System.out.println(order);
        return orderRepository.save(order) == 1;
    }
}
