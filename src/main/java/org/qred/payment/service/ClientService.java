package org.qred.payment.service;

import org.qred.payment.domain.ClientCreateDTO;
import org.qred.payment.domain.ClientDTO;

import java.util.List;

public interface ClientService {
    List<ClientDTO> findAll();
    ClientDTO findById(Long id);
    ClientDTO save(ClientCreateDTO dto);
    ClientDTO update(Long id, ClientDTO dto);
}
