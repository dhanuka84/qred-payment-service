package org.qred.payment.validator;

import java.text.MessageFormat;
import java.util.Set;

import org.qred.payment.domain.ContractDTO;
import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.exception.Http400BadRequest;
import org.springframework.stereotype.Component;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */
@Component
@Slf4j
public class PaymentValidator {
	
	 private final Validator validator;

	    public PaymentValidator(Validator validator) {
	        this.validator = validator;
	    }

	
	 public void validatePaymentRequest(PaymentDTO dto) {
	        Set<ConstraintViolation<PaymentDTO>> violations = validator.validate(dto);
	        if (!violations.isEmpty()) {
	            StringBuilder sb = new StringBuilder();
	            for (ConstraintViolation<PaymentDTO> violation : violations) {
	                sb.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
	            }
	            throw new IllegalArgumentException("Validation failed: " + sb.toString());
	        }
	    }

}
