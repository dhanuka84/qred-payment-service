package org.qred.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import org.qred.payment.domain.ContractDTO;
import org.qred.payment.entity.Client;
import org.qred.payment.entity.Contract;
import org.qred.payment.mapper.ContractMapper;
import org.qred.payment.repository.ClientRepository;
import org.qred.payment.repository.ContractRepository;
import org.qred.payment.service.impl.ContractServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ClientRepository clientRepository;

    @Spy
    private ContractMapper contractMapper = Mappers.getMapper(ContractMapper.class);

    @InjectMocks
    private ContractServiceImpl contractService;

    private Contract contract;
    private ContractDTO contractDTO;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "Acme");
        contract = new Contract(1L, client, "12345");
        contractDTO = new ContractDTO(1L, 1L, "12345");
    }

    @Test
    void shouldReturnAllContracts() {
        when(contractRepository.findAll()).thenReturn(List.of(contract));
        List<ContractDTO> result = contractService.findAll();
        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).contractNumber());
    }

    @Test
    void shouldReturnContractById() {
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        ContractDTO result = contractService.findById(1L);
        assertEquals("12345", result.contractNumber());
    }

    @Test
    void shouldSaveContract() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(contractRepository.save(any())).thenReturn(contract);
        ContractDTO result = contractService.save(contractDTO);
        assertEquals("12345", result.contractNumber());
    }

    @Test
    void shouldUpdateContract() {
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(contractRepository.save(any())).thenReturn(contract);
        ContractDTO result = contractService.update(1L, contractDTO);
        assertEquals("12345", result.contractNumber());
    }
} 
