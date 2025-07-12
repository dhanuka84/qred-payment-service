package org.qred.payment.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.qred.payment.domain.ContractCreateDTO;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.qred.payment.domain.ContractDTO;
import org.qred.payment.entity.Contract;
import org.qred.payment.mapper.ContractMapper;
import org.qred.payment.repository.ContractRepository;
import org.qred.payment.service.ContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository repository;
    private final ContractMapper mapper;

    public ContractServiceImpl(ContractRepository repository, ContractMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ContractDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public ContractDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ContractDTO save(ContractCreateDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ContractDTO update(Long id, ContractDTO dto) {
        Contract contract = repository.findById(id).orElseThrow();
        contract.setContractNumber(dto.contractNumber());
        return mapper.toDTO(repository.save(contract));
    }

	@Override
	public Optional<Contract> findByContractNumber(String contractNumber) {
		return repository.findByContractNumber(contractNumber);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Contract> findAllByContractNumbers(Set<String> contractNumbers) {
		return repository.findAllByContractNumberIn(contractNumbers);
	}
}
