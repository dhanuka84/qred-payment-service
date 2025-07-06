
package org.qred.payment.domain;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
    String transactionId,
    String status,
    BigDecimal amount,
    String currency,
    LocalDateTime timestamp
) {}
