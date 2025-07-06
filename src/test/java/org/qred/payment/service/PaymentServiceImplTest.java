package org.qred.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.entity.Client;
import org.qred.payment.entity.Contract;
import org.qred.payment.entity.Payment;
import org.qred.payment.mapper.PaymentMapper;
import org.qred.payment.repository.ContractRepository;
import org.qred.payment.repository.PaymentRepository;
import org.qred.payment.service.impl.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ContractRepository contractRepository;

    @Spy
    private PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;
    private PaymentDTO paymentDTO;
    private Contract contract;

    @BeforeEach
    void setUp() {
    	paymentMapper.setContractRepository(contractRepository);
    	
        contract = new Contract(1L, new Client(1L, "Acme"), "12345");
        payment = new Payment(1L, LocalDate.of(2024, 1, 30), 1000.0, "incoming", contract);
        paymentDTO = new PaymentDTO("2024-01-30", 1000.0, "incoming", "12345");
    }

    @Test
    void shouldReturnAllPayments() {
        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        List<PaymentDTO> result = paymentService.findAll();
        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).contract_number());
    }

    @Test
    void shouldReturnPaymentById() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        PaymentDTO result = paymentService.findById(1L);
        assertEquals("12345", result.contract_number());
    }

    @Test
    void shouldSavePayment() {
        when(contractRepository.findByContractNumber("12345")).thenReturn(Optional.of(contract));
        when(paymentRepository.save(any())).thenReturn(payment);
        PaymentDTO result = paymentService.save(paymentDTO);
        assertEquals("12345", result.contract_number());
    }

    @Test
    void shouldUpdatePayment() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any())).thenReturn(payment);
        PaymentDTO result = paymentService.update(1L, paymentDTO);
        assertEquals("12345", result.contract_number());
    }
} 
