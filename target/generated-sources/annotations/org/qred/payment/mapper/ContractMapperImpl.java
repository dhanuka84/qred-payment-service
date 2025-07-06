package org.qred.payment.mapper;

import javax.annotation.processing.Generated;
import org.qred.payment.domain.ContractDTO;
import org.qred.payment.entity.Client;
import org.qred.payment.entity.Contract;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T02:39:16+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class ContractMapperImpl implements ContractMapper {

    @Override
    public ContractDTO toDTO(Contract contract) {
        if ( contract == null ) {
            return null;
        }

        Long clientId = null;
        Long contractId = null;
        String contractNumber = null;

        clientId = contractClientClientId( contract );
        contractId = contract.getContractId();
        contractNumber = contract.getContractNumber();

        ContractDTO contractDTO = new ContractDTO( contractId, clientId, contractNumber );

        return contractDTO;
    }

    @Override
    public Contract toEntity(ContractDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Contract contract = new Contract();

        contract.setClient( contractDTOToClient( dto ) );
        contract.setContractId( dto.contractId() );
        contract.setContractNumber( dto.contractNumber() );

        return contract;
    }

    private Long contractClientClientId(Contract contract) {
        if ( contract == null ) {
            return null;
        }
        Client client = contract.getClient();
        if ( client == null ) {
            return null;
        }
        Long clientId = client.getClientId();
        if ( clientId == null ) {
            return null;
        }
        return clientId;
    }

    protected Client contractDTOToClient(ContractDTO contractDTO) {
        if ( contractDTO == null ) {
            return null;
        }

        Client client = new Client();

        client.setClientId( contractDTO.clientId() );

        return client;
    }
}
