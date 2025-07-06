
package org.qred.payment.repository;

import java.util.List;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import org.qred.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	@Query(value = """
			    SELECT p.*
			    FROM payment p
			    JOIN contract c ON p.contract_id = c.contract_id
			    WHERE c.contract_number = :contractNumber
			""", nativeQuery = true)
	List<Payment> findPaymentsByContractNumber(@Param("contractNumber") String contractNumber);

}
