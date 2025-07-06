
package org.qred.payment.mapper;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.qred.payment.domain.ContractDTO;
import org.qred.payment.entity.Client;
import org.qred.payment.entity.Contract;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(source = "client.clientId", target = "clientId")
    ContractDTO toDTO(Contract contract);

    @Mapping(source = "clientId", target = "client.clientId")
    Contract toEntity(ContractDTO dto);
}
