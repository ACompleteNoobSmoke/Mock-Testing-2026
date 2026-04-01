package com.noobsmoke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceTest {

    @Mock
    private Clock clock;

    @Mock
    private Duration duration;

    @InjectMocks
    private VerificationCodeService underTest;

    @Test
    void isExpiredFalseTest() {
        given(clock.getZone()).willReturn(ZoneId.of("America/Chicago"));
        given(clock.instant()).willReturn(Instant.now());
        VerificationCode code = new VerificationCode("Jackie Chan Adventures", ZonedDateTime.now(clock));
        assertFalse(underTest.isExpired(code));
    }

    @Test
    void isExpiredTrueTest() {
        given(clock.getZone()).willReturn(ZoneId.of("America/Chicago"));
        given(clock.instant()).willReturn(ZonedDateTime.now().plusMinutes(16).toInstant());
        VerificationCode code = new VerificationCode("Jackie Chan Adventures", ZonedDateTime.now(clock));
        assertFalse(underTest.isExpired(code));
    }


}