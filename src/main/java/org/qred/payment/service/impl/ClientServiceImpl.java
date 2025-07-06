package org.qred.payment.service.impl;

import java.util.List;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.qred.payment.domain.ClientDTO;
import org.qred.payment.entity.Client;
import org.qred.payment.mapper.ClientMapper;
import org.qred.payment.repository.ClientRepository;
import org.qred.payment.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ClientDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public ClientDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ClientDTO save(ClientDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ClientDTO update(Long id, ClientDTO dto) {
        Client client = repository.findById(id).orElseThrow();
        client.setClientName(dto.clientName());
        return mapper.toDTO(repository.save(client));
    }
}
