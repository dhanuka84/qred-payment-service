package org.qred.payment.validator;

import java.text.MessageFormat;

import org.qred.payment.domain.ContractCreateDTO;
import org.qred.payment.domain.ContractDTO;
import org.qred.payment.exception.Http400BadRequest;
import org.springframework.stereotype.Component;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */
@Component
@Slf4j
public class CotractValidator {

	public void validateUpdateRequest(Long id, ContractDTO contractDTO) {
		if (contractDTO.contractId() != id) {
			throw new Http400BadRequest(MessageFormat.format(
					"Id {0} in request parameter not matched with the Request body {1}", id, contractDTO.contractId()));
		}
	}
	
	
	
	public void validateCreate(ContractCreateDTO contractDTO) {
		
		if (StringUtils.isBlank(contractDTO.contractNumber())) {
			throw new Http400BadRequest(MessageFormat.format(
					"Request body missing the contract number {0}", contractDTO.contractNumber()));
		}
		
		if (contractDTO.clientId() == null) {
			throw new Http400BadRequest(MessageFormat.format(
					"Request body missing the client id {0}", contractDTO.clientId()));
		}
	}


}
