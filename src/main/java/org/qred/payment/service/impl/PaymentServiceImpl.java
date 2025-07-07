package org.qred.payment.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import org.qred.payment.validator.PaymentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	private final PaymentRepository repository;
	private final PaymentMapper mapper;
	private final ContractService contractService;

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public PaymentServiceImpl(PaymentRepository repository, ContractService contractService, PaymentMapper mapper) {
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
	@Transactional(propagation = Propagation.REQUIRED)
	public PaymentDTO save(PaymentDTO dto) {
		return mapper.toDTO(repository.save(mapper.toEntity(dto)));
	}

	@Async
    @Override
    public CompletableFuture<List<PaymentDTO>> saveAsynch(List<PaymentDTO> payments, PaymentValidator validator) {
        
        // Validate and collect only valid DTOs
        List<PaymentDTO> validPayments = payments.stream()
            .filter(dto -> {
                try {
                    validator.validatePaymentRequest(dto);
                    return true;
                } catch (Exception e) {
                    log.warn("Validation failed for PaymentDTO: {}", dto, e);
                    return false;
                }
            })
            .toList();

        if (validPayments.isEmpty()) {
            log.warn("No valid PaymentDTOs to process.");
            return CompletableFuture.completedFuture(List.of());
        }

        Map<String, List<PaymentDTO>> paymentsByContracts = validPayments.stream()
                .collect(Collectors.groupingBy(PaymentDTO::contract_number));

        Set<String> contractNumbers = paymentsByContracts.keySet();

        List<Contract> contracts = contractService.findAllByContractNumbers(contractNumbers);
        Map<String, Contract> contractsByContractNumber = contracts.stream()
                .collect(Collectors.toMap(Contract::getContractNumber, Function.identity()));

        List<Payment> paymentEntities = validPayments.stream()
                .map(dto -> {
                    Contract contract = contractsByContractNumber.get(dto.contract_number());
                    if (contract == null) {
                        log.error("Contract not found for number: {}", dto.contract_number());
                        throw new IllegalArgumentException("Contract not found for number: " + dto.contract_number());
                    }
                    return mapper.toEntity(dto, contract);
                })
                .collect(Collectors.toList());

        return savePayments(paymentEntities);
    }
	
	@Transactional(propagation = Propagation.REQUIRED)
	private CompletableFuture<List<PaymentDTO>> savePayments(List<Payment> paymentEntities){
		// Save all Payment entities
	    List<Payment> savedPayments = repository.saveAll(paymentEntities);

	    // Convert back to DTOs
	    List<PaymentDTO> savedDtos = savedPayments.stream()
	            .map(mapper::toDTO)
	            .collect(Collectors.toList());

	    return CompletableFuture.completedFuture(savedDtos);

	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public PaymentDTO update(Long id, PaymentDTO dto) {
		Payment payment = repository.findById(id).orElseThrow();

		// Parse date from String
		LocalDate paymentDate = LocalDate.parse(dto.paymentDate(), DATE_FORMATTER);
		payment.setPaymentDate(paymentDate);
		payment.setAmount(dto.amount());
		payment.setType(dto.type());

		// Update contract
		Contract contract = contractService.findByContractNumber(dto.contract_number())
				.orElseThrow(() -> new IllegalArgumentException("Contract not found: " + dto.contract_number()));
		payment.setContract(contract);

		return mapper.toDTO(repository.save(payment));
	}

	@Override
	public List<PaymentDTO> findPaymentsByContractNumber(String contractNumber) {
		List<Payment> payments = repository.findPaymentsByContractNumber(contractNumber);
		return payments.stream().map(mapper::toDTO).collect(Collectors.toList());
	}

}
