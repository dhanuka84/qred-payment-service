package org.qred.payment.validator;

import java.util.Set;
import java.util.regex.Pattern;

import org.qred.payment.domain.PaymentDTO;
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

	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");
	private final Validator validator;

	public PaymentValidator(Validator validator) {
		this.validator = validator;
	}
	
	public void validateContractNumber(String contractNumber) {
		if(StringUtils.isBlank(contractNumber) || WHITESPACE_PATTERN.matcher(contractNumber).find()) throw new IllegalArgumentException("Validation failed: "); 
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
