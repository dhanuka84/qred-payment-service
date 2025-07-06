
package org.qred.payment.domain;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import java.math.BigDecimal;

public record PaymentRequest(
    String senderAccountId,
    String receiverAccountId,
    BigDecimal amount,
    String currency,
    String description
) {}
