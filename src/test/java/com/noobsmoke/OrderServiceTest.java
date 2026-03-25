package com.noobsmoke;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

public class OrderServiceTest {

    private OrderService underTest;
    @Mock
    private PaymentProcessor stripePaymentProcessor;

    @BeforeEach
    void setUp() {
        underTest = new OrderService(stripePaymentProcessor);
    }
}
