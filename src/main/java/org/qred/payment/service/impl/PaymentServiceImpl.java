package org.qred.payment.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.entity.Contract;
import org.qred.payment.entity.Payment;
import org.qred.payment.mapper.PaymentMapper;
import org.qred.payment.repository.PaymentRepository;
import org.qred.payment.service.ContractService;
import org.qred.payment.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final ContractService contractService;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PaymentServiceImpl(PaymentRepository repository,ContractService contractService, PaymentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.contractService = contractService;
    }

    @Override
    public List<PaymentDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public PaymentDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow();
    }

    @Override
    public PaymentDTO save(PaymentDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public PaymentDTO update(Long id, PaymentDTO dto) {
        Payment payment = repository.findById(id).orElseThrow();

        // Parse date from String
        LocalDate paymentDate = LocalDate.parse(dto.paymentDate(),DATE_FORMATTER);
        payment.setPaymentDate(paymentDate);
        payment.setAmount(dto.amount());
        payment.setType(dto.type());

        // Update contract
        Contract contract = contractService.findByContractNumber(dto.contract_number())
            .orElseThrow(() -> new IllegalArgumentException("Contract not found: " + dto.contract_number()));
        payment.setContract(contract);

        return mapper.toDTO(repository.save(payment));
    }

}
