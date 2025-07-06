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
public class ClientValidator {
	
	public void validateClientNameRequest(String name) {
		if (StringUtils.isBlank(name)) {
			throw new Http400BadRequest("Invalid Amount.");
		}
	}

}
