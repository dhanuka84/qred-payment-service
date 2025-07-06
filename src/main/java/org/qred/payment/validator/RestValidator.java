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
public class RestValidator {
	
	 private final Validator validator;

	    public RestValidator(Validator validator) {
	        this.validator = validator;
	    }

	public void validateUpdateRequest(Long id, ContractDTO contractDTO) {
		if (contractDTO.contractId() != id) {
			throw new Http400BadRequest(MessageFormat.format(
					"Id {0} in request parameter not matched with the Request body {1}", id, contractDTO.contractId()));
		}
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
	
	
	public void validateCreate(ContractDTO contractDTO) {
		if (contractDTO.contractId() != null) {
			throw new Http400BadRequest(MessageFormat.format(
					"Request body can not have contractId {0}", contractDTO.contractId()));
		}
		
		if (StringUtils.isBlank(contractDTO.contractNumber())) {
			throw new Http400BadRequest(MessageFormat.format(
					"Request body missing the contract number {0}", contractDTO.contractNumber()));
		}
		
		if (contractDTO.clientId() == null) {
			throw new Http400BadRequest(MessageFormat.format(
					"Request body missing the client id {0}", contractDTO.clientId()));
		}
	}

	public void validateClientNameRequest(String name) {
		if (StringUtils.isBlank(name)) {
			throw new Http400BadRequest("Invalid Amount.");
		}
	}

}
