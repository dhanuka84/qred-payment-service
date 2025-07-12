package org.qred.payment.service;

import java.util.List;

import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.util.parser.PaymentFileParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaymentFileUploadService {

    private final List<PaymentFileParser> parsers;

    public PaymentFileUploadService(List<PaymentFileParser> parsers) {
        this.parsers = parsers;
    }

    public List<PaymentDTO> processFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();

        PaymentFileParser parser = parsers.stream()
            .filter(p -> p.supports(filename))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported file format"));

        return parser.parse(file.getInputStream());
    }
}
