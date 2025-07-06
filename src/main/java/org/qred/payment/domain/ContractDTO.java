
package org.qred.payment.domain;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

public record ContractDTO(
    Long contractId,
    Long clientId,
    String contractNumber
) {}
