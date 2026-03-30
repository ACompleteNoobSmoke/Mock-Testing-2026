package com.noobsmoke;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record Order(
        Integer ID,
        User user,
        BigDecimal amount,
        ZonedDateTime zonedDateTime
) {
}
