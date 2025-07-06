package org.qred.payment.mapper;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.mapstruct.*;
import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.entity.Contract;
import org.qred.payment.entity.Payment;
import org.qred.payment.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    protected ContractRepository contractRepository;

    @Mapping(source = "contract.contractNumber", target = "contract_number")
    @Mapping(source = "paymentDate", target = "paymentDate", qualifiedByName = "localDateToString")
    public abstract PaymentDTO toDTO(Payment payment);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(source = "contract_number", target = "contract", qualifiedByName = "contractFromNumber")
    @Mapping(source = "paymentDate", target = "paymentDate", qualifiedByName = "stringToLocalDate")
    public abstract Payment toEntity(PaymentDTO dto);
    
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(source = "paymentDate", target = "paymentDate", qualifiedByName = "stringToLocalDate")
    public abstract Payment toEntity(PaymentDTO dto, @Context Contract contract);

    @AfterMapping
    protected void setContract(@MappingTarget Payment payment, @Context Contract contract) {
        payment.setContract(contract);
    }

    @Named("stringToLocalDate")
    protected LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    @Named("localDateToString")
    protected String localDateToString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    @Named("contractFromNumber")
    protected Contract contractFromNumber(String contractNumber) {
        return contractRepository.findByContractNumber(contractNumber)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found: " + contractNumber));
    }
    
    public void setContractRepository(ContractRepository contractRepository) {
    	this.contractRepository = contractRepository;
    }
}
