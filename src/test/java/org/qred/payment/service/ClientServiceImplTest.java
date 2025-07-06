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
import org.qred.payment.domain.ClientCreateDTO;
import org.qred.payment.domain.ClientDTO;
import org.qred.payment.entity.Client;
import org.qred.payment.mapper.ClientMapper;
import org.qred.payment.repository.ClientRepository;
import org.qred.payment.service.impl.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Spy
    private ClientMapper clientMapper =  Mappers.getMapper( ClientMapper.class );

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientDTO clientDTO;
    private ClientCreateDTO clientCreateDTO;

    @BeforeEach
    void setUp() {
        client = new Client(1L, "Acme");
        clientDTO = new ClientDTO(1L, "Acme");
        clientCreateDTO = new ClientCreateDTO("Acme");
    }

    @Test
    void shouldReturnAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        List<ClientDTO> result = clientService.findAll();
        assertEquals(1, result.size());
        assertEquals("Acme", result.get(0).clientName());
    }

    @Test
    void shouldReturnClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        ClientDTO result = clientService.findById(1L);
        assertEquals("Acme", result.clientName());
    }

    @Test
    void shouldSaveClient() {
        when(clientRepository.save(any())).thenReturn(client);
        ClientDTO result = clientService.save(clientCreateDTO);
        assertEquals("Acme", result.clientName());
    }

    @Test
    void shouldUpdateClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any())).thenReturn(client);
        ClientDTO result = clientService.update(1L, clientDTO);
        assertEquals("Acme", result.clientName());
    }
}
