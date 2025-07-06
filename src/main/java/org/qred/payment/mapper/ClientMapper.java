
package org.qred.payment.mapper;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.mapstruct.Mapper;
import org.qred.payment.domain.ClientDTO;
import org.qred.payment.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDTO(Client client);
    Client toEntity(ClientDTO dto);
}
