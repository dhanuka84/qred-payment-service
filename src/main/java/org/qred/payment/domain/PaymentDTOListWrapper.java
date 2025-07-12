package org.qred.payment.domain;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "payments")
public class PaymentDTOListWrapper {

    @XmlElement(name = "payment")
    private List<PaymentDTO> payments;

    public PaymentDTOListWrapper() {}

    public List<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }
}

