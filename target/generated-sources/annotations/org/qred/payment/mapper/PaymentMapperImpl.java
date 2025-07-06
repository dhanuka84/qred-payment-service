package org.qred.payment.mapper;

import javax.annotation.processing.Generated;
import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.entity.Contract;
import org.qred.payment.entity.Payment;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T22:12:01+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl extends PaymentMapper {

    @Override
    public PaymentDTO toDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        String contract_number = null;
        String paymentDate = null;
        double amount = 0.0d;
        String type = null;

        contract_number = paymentContractContractNumber( payment );
        paymentDate = localDateToString( payment.getPaymentDate() );
        amount = payment.getAmount();
        type = payment.getType();

        PaymentDTO paymentDTO = new PaymentDTO( paymentDate, amount, type, contract_number );

        return paymentDTO;
    }

    @Override
    public Payment toEntity(PaymentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setContract( contractFromNumber( dto.contract_number() ) );
        payment.setPaymentDate( stringToLocalDate( dto.paymentDate() ) );
        payment.setAmount( dto.amount() );
        payment.setType( dto.type() );

        return payment;
    }

    @Override
    public Payment toEntity(PaymentDTO dto, Contract contract) {
        if ( dto == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setPaymentDate( stringToLocalDate( dto.paymentDate() ) );
        payment.setAmount( dto.amount() );
        payment.setType( dto.type() );

        setContract( payment, contract );

        return payment;
    }

    private String paymentContractContractNumber(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Contract contract = payment.getContract();
        if ( contract == null ) {
            return null;
        }
        String contractNumber = contract.getContractNumber();
        if ( contractNumber == null ) {
            return null;
        }
        return contractNumber;
    }
}
