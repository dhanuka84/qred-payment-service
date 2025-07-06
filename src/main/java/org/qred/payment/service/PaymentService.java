package org.qred.payment.service;

import org.qred.payment.domain.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> findAll();
    PaymentDTO findById(Long id);
    PaymentDTO save(PaymentDTO dto);
    PaymentDTO update(Long id, PaymentDTO dto);
}
