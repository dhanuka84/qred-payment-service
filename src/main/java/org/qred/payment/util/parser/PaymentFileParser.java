package org.qred.payment.util.parser;

import java.io.InputStream;
import java.util.List;

import org.qred.payment.domain.PaymentDTO;

public interface PaymentFileParser {
    List<PaymentDTO> parse(InputStream inputStream) throws Exception;
    boolean supports(String filename);
}
