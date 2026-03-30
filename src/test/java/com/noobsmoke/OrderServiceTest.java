package com.noobsmoke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService underTest;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PaymentProcessor stripePaymentProcessor;
    @Mock
    private Random random;


//    @BeforeEach
//    void setUp() {
//        underTest = new OrderService(stripePaymentProcessor);
//    }

    @Test
    void amountIsChargedTest() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1000.12);
        User user = new User(1, "Osaretin");
        Order order = new Order(1, user, BigDecimal.ONE, ZonedDateTime.now());
        when(stripePaymentProcessor.charge(bigDecimal)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        boolean answer = underTest.processOrder(user, bigDecimal);
        verify(stripePaymentProcessor).charge(bigDecimal);
        assertTrue(answer);
    }

    @Test
    void amountThrowsException() {
        BigDecimal decimal = BigDecimal.ONE;
        when(stripePaymentProcessor.charge(decimal)).thenReturn(false);
        IllegalStateException stateException = assertThrows(IllegalStateException.class,
                () -> underTest.processOrder(any(User.class), decimal));
        assertEquals("Payment Failed", stateException.getMessage());
        verify(stripePaymentProcessor).charge(decimal);
    }

    @Test
    void testAnyMatcher() {
        Map<String, String> mockMap = mock();
        when(mockMap.get(anyString())).thenReturn("Hello");
        assertThat(mockMap.get("Vegeta")).isEqualTo("Hello");
        verify(mockMap, times(1)).get(anyString());
    }

    @Test
    void testEqMatcher() {
        Map<String, String> mockMap = mock();
        when(mockMap.put(anyString(), eq("Jackie Chan"))).thenReturn("Hello");
        assertThat(mockMap.put("Hello", "Jackie Chan")).isEqualTo("Hello");
        verify(mockMap).put("Hello", "Jackie Chan");
    }

}
