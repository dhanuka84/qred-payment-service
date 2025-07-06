package org.qred.payment.domain;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record PaymentDTO(
    
    @NotBlank(message = "Payment date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in yyyy-MM-dd format")
    String paymentDate,

    @Positive(message = "Amount must be greater than 0")
    double amount,

    @NotBlank(message = "Payment type is required")
    String type,

    @NotBlank(message = "Contract number is required")
    String contract_number

) {}
