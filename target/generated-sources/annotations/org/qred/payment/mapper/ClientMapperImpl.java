package org.qred.payment.mapper;

import javax.annotation.processing.Generated;
import org.qred.payment.domain.ClientCreateDTO;
import org.qred.payment.domain.ClientDTO;
import org.qred.payment.entity.Client;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T22:12:01+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDTO toDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        Long clientId = null;
        String clientName = null;

        clientId = client.getClientId();
        clientName = client.getClientName();

        ClientDTO clientDTO = new ClientDTO( clientId, clientName );

        return clientDTO;
    }

    @Override
    public Client toEntity(ClientDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Client client = new Client();

        client.setClientId( dto.clientId() );
        client.setClientName( dto.clientName() );

        return client;
    }

    @Override
    public Client toEntity(ClientCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Client client = new Client();

        client.setClientName( dto.clientName() );

        return client;
    }
}
