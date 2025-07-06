
package org.qred.payment.entity;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import jakarta.persistence.*;

@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    private String contractNumber;

    public Contract() {}
    public Contract(Long contractId, Client client, String contractNumber) {
        this.contractId = contractId;
        this.client = client;
        this.contractNumber = contractNumber;
    }

    public Long getContractId() { return contractId; }
    public void setContractId(Long contractId) { this.contractId = contractId; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public String getContractNumber() { return contractNumber; }
    public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }
}
