package com.noobsmoke;

import java.math.BigDecimal;

public interface PaymentProcessor {
    boolean charge(BigDecimal amount);
}
