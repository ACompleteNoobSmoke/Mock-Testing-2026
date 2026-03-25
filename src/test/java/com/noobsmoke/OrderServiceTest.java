package com.noobsmoke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService underTest;
    @Mock
    private PaymentProcessor stripePaymentProcessor;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new OrderService(stripePaymentProcessor);
    }

    @Test
    void amountIsChargedTest() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1000.12);
        when(stripePaymentProcessor.charge(bigDecimal)).thenReturn(true);
        boolean answer = underTest.processOrder(bigDecimal);
        verify(stripePaymentProcessor).charge(bigDecimal);
        assertTrue(answer);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
}
