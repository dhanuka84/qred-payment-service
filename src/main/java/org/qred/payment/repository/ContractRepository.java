
package org.qred.payment.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.qred.payment.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, Long> {

	Optional<Contract> findByContractNumber(String contractNumber);

	@Query(value = """
			SELECT c.*,
			       cl.client_id as cl_client_id,
			       cl.client_name as cl_client_name
			FROM contract c
			JOIN client cl ON c.client_id = cl.client_id
			WHERE c.contract_number IN (:contractNumbers)
			""", nativeQuery = true)
	List<Contract> findAllByContractNumberIn(@Param("contractNumbers") Set<String> contractNumbers);

}
