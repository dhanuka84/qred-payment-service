
package org.qred.payment.entity;

import java.util.Objects;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private String contractNumber;

    public Contract() {}
    public Contract(Long contractId, Client client, String contractNumber) {
        this.contractId = contractId;
        this.client = client;
        this.contractNumber = contractNumber;
    }
    
    

    @Override
	public int hashCode() {
		return Objects.hash(contractId, contractNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		return Objects.equals(contractId, other.contractId) && Objects.equals(contractNumber, other.contractNumber);
	}
	
	public Long getContractId() { return contractId; }
    public void setContractId(Long contractId) { this.contractId = contractId; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public String getContractNumber() { return contractNumber; }
    public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }
    
    
}
