package org.qred.payment.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "payment")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentDTO {

    @XmlElement(name = "payment_date")
    @NotBlank(message = "Payment date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Payment date must be in yyyy-MM-dd format")
    private String paymentDate;

    @XmlElement
    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @XmlElement
    @NotBlank(message = "Type is required")
    private String type;

    @XmlElement(name = "contract_number")
    @NotBlank(message = "Contract number is required")
    private String contractNumber;

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	
	public PaymentDTO(
			@NotBlank(message = "Payment date is required") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Payment date must be in yyyy-MM-dd format") String paymentDate,
			@Positive(message = "Amount must be greater than zero") double amount,
			@NotBlank(message = "Type is required") String type,
			@NotBlank(message = "Contract number is required") String contractNumber) {
		super();
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.type = type;
		this.contractNumber = contractNumber;
	}

	public PaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


    
    
  
}
