package org.qred.payment.service;

import java.util.List;
import java.util.Optional;

import org.qred.payment.domain.ContractCreateDTO;
import org.qred.payment.domain.ContractDTO;
import org.qred.payment.entity.Contract;

public interface ContractService {
    List<ContractDTO> findAll();
    ContractDTO findById(Long id);
    ContractDTO save(ContractCreateDTO dto);
    ContractDTO update(Long id, ContractDTO dto);
    Optional<Contract> findByContractNumber(String contractNumber);
}
