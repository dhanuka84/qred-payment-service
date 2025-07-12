package org.qred.payment.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.entity.Contract;
import org.qred.payment.entity.Payment;
import org.qred.payment.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PaymentMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    protected ContractRepository contractRepository;

    @Mapping(source = "contract.contractNumber", target = "contractNumber")
    @Mapping(source = "paymentDate", target = "paymentDate", qualifiedByName = "localDateToString")
    public abstract PaymentDTO toDTO(Payment payment);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(source = "contractNumber", target = "contract", qualifiedByName = "contractFromNumber")
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

    // Optional setter for tests or manual instantiation
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
}
