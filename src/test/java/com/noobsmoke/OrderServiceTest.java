package com.noobsmoke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;


//    @BeforeEach
//    void setUp() {
//        underTest = new OrderService(stripePaymentProcessor);
//    }

    @Test
    void mockitoDBBExample() {
        List<String> mockList = mock();

        given(mockList.get(0)).willReturn("Spiderman");

        String actual = mockList.get(0);

        then(mockList).should().get(0);

        assertThat(actual).isEqualTo("Spiderman");

    }

    @Test
    void shouldThrowWhenChargeFailsWithMockitoBDD() {
        given(stripePaymentProcessor.charge(any(BigDecimal.class))).willReturn(false);
        String exMessage = assertThrows(IllegalStateException.class, () -> underTest.processOrder(any(User.class), BigDecimal.ONE)).getMessage();
        assertEquals("Payment Failed", exMessage);
        then(stripePaymentProcessor).should().charge(any(BigDecimal.class));
        then(orderRepository).shouldHaveNoInteractions();
    }

    @Test
    void chainedStubbing() {
        List<String> mockList = mock();

        given(mockList.size()).willReturn(1, 2, 3, 4);

        assertThat(mockList.size()).isEqualTo(1);
        assertThat(mockList.size()).isEqualTo(2);
        assertThat(mockList.size()).isEqualTo(3);
        assertThat(mockList.size()).isEqualTo(4);
        assertThat(mockList.size()).isEqualTo(4);
    }

    @Test
    void shouldReturnCustomAnswer() {
        List<String> mockList = mock();
        given(mockList.get(anyInt())).will(invocationOnMock ->
                "ACompleteNoobSmoke " + invocationOnMock.getArgument(0));
        assertThat(mockList.get(0)).isEqualTo("ACompleteNoobSmoke 0");
        assertThat(mockList.get(1)).isEqualTo("ACompleteNoobSmoke 1");
    }

    @Test
    void amountIsChargedTest() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1000.12);
        User user = new User(1, "Osaretin");
        when(stripePaymentProcessor.charge(bigDecimal)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        boolean answer = underTest.processOrder(user, bigDecimal);
        verify(stripePaymentProcessor).charge(bigDecimal);
        verify(orderRepository).save(assertArg(order1 -> {
            assertThat(order1.amount()).isEqualTo(bigDecimal);
            assertThat(order1.user()).isEqualTo(user);
        }));
        assertTrue(answer);
    }

    @Test
    void amountIsChargedWithArgCaptorTest() {
        BigDecimal bigDecimal = BigDecimal.valueOf(1000.12);
        User user = new User(1, "Osaretin");
        when(stripePaymentProcessor.charge(bigDecimal)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        boolean answer = underTest.processOrder(user, bigDecimal);
        verify(stripePaymentProcessor).charge(bigDecimal);
//        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order captoredOrder = orderArgumentCaptor.getValue();
        assertThat(captoredOrder.amount()).isEqualTo(bigDecimal);
        assertThat(captoredOrder.user()).isEqualTo(user);
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

    @Test
    void async() {
        Runnable mockRunnable = mock();

        Executors.
                newSingleThreadScheduledExecutor()
                .schedule(mockRunnable, 200, TimeUnit.MILLISECONDS);

        BDDMockito.then(mockRunnable).should(timeout(200)).run();
    }

    @Test
    void canAdvanceClock() {
        Clock clock = mock();
        ZoneId zonedId = ZoneId.of("Europe/London");
        ZonedDateTime fixedZDT = ZonedDateTime.of(2027, 1, 1, 0, 0, 0, 0, zonedId);
        given(clock.getZone()).willReturn(zonedId);
        given(clock.instant()).willReturn(fixedZDT.toInstant());
        ZonedDateTime zonedDateTime = ZonedDateTime.now(clock);
        System.out.println(zonedDateTime);
        given(clock.instant()).willReturn(zonedDateTime.plusMinutes(15).toInstant());
        System.out.println(ZonedDateTime.now(clock));

    }

}
