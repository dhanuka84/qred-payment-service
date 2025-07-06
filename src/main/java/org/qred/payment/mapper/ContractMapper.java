package org.qred.payment.mapper;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.qred.payment.domain.ContractDTO;
import org.qred.payment.entity.Client;
import org.qred.payment.entity.Contract;
import org.qred.payment.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ContractMapper {


	@Autowired
    protected ClientRepository clientRepository;

	@Mapping(source = "client.clientId", target = "clientId")
    public abstract ContractDTO toDTO(Contract contract);

    @Mapping(target = "contractId", source = "contractId")
    @Mapping(source = "clientId", target = "client", qualifiedByName = "mapClientIdToClient")
    public abstract Contract toEntity(ContractDTO dto);

    @Named("mapClientIdToClient")
    protected Client mapClientIdToClient(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found for id: " + clientId));
    }
    
 // In ContractMapper.java
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

}
