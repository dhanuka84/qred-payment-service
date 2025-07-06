package org.qred.payment.service;

import org.qred.payment.domain.PaymentDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {
    List<PaymentDTO> findAll();
    List<PaymentDTO> findPaymentsByContractNumber(String contractNumber);
    
    PaymentDTO findById(Long id);
    PaymentDTO save(PaymentDTO dto);
    PaymentDTO update(Long id, PaymentDTO dto);
	CompletableFuture<List<PaymentDTO>> saveAsynch(List<PaymentDTO> dto);
}
