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
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService underTest;
    @Mock
    private PaymentProcessor stripePaymentProcessor;


//    @BeforeEach
//    void setUp() {
//        underTest = new OrderService(stripePaymentProcessor);
//    }

    @Test
    void amountIsChargedTest() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1000.12);
        when(stripePaymentProcessor.charge(bigDecimal)).thenReturn(true);
        boolean answer = underTest.processOrder(bigDecimal);
        verify(stripePaymentProcessor).charge(bigDecimal);
        assertTrue(answer);
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
        ;
        assertThat(mockMap.put("Hello", "Jackie Chan")).isEqualTo("Hello");
        verify(mockMap).put("Hello", "Jackie Chan");
    }

}
