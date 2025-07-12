package org.qred.payment.util.parser;

import java.io.InputStream;
import java.util.List;

import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.domain.PaymentDTOListWrapper;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@Component
public class XmlPaymentFileParser implements PaymentFileParser {
    public boolean supports(String filename) {
        return filename.toLowerCase().endsWith(".xml");
    }

    public List<PaymentDTO> parse(InputStream inputStream)throws Exception {
        // XML parsing logic
    	JAXBContext context = JAXBContext.newInstance(PaymentDTOListWrapper.class);
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    PaymentDTOListWrapper wrapper = (PaymentDTOListWrapper) unmarshaller.unmarshal(inputStream);
	    return wrapper.getPayments();
    }
}