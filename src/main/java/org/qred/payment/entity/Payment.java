
package org.qred.payment.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    
    @Version
    @Column(name = "version", nullable = false)
    private Integer version;
    
    private LocalDate paymentDate;
    private double amount;
    private String type;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public Payment() {}
    public Payment(Long paymentId, LocalDate paymentDate, double amount, String type, Contract contract) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.type = type;
        this.contract = contract;
    }
    
    

    
	
	@Override
	public int hashCode() {
		return Objects.hash(amount, contract, paymentDate, paymentId, type);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(contract, other.contract) && Objects.equals(paymentDate, other.paymentDate)
				&& Objects.equals(paymentId, other.paymentId) && Objects.equals(type, other.type);
	}
	
	public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
