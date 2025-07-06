
package org.qred.payment.repository;

import java.util.Optional;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.qred.payment.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	
	
	Optional<Contract> findByContractNumber(String contractNumber);
}
