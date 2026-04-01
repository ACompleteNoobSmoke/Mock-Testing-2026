package com.noobsmoke;

import java.time.Clock;
import java.time.Duration;
import java.time.ZonedDateTime;

public class VerificationCodeService {
    private final Clock clock;
    private final Duration expiryduration;

    public VerificationCodeService(Clock clock,  Duration duration) {
        this.clock = clock;
        this.expiryduration = duration;
    }

    public boolean isExpired(VerificationCode code) {
        ZonedDateTime now = ZonedDateTime.now(clock);
        Duration elapseed = Duration.between(code.createdAt(), now);
        return elapseed.compareTo(expiryduration) > 0;
    }
}
