package org.qred.payment.util.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.qred.payment.domain.PaymentDTO;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

@Component
public class CsvPaymentFileParser implements PaymentFileParser {
    public boolean supports(String filename) {
        return filename.toLowerCase().endsWith(".csv");
    }

    public List<PaymentDTO> parse(InputStream inputStream) throws Exception {
    	List<PaymentDTO> payments = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
			String[] header = reader.readNext(); // skip header
			String[] row;

			while ((row = reader.readNext()) != null) {
				if (row.length < 4)
					continue;

				PaymentDTO dto = new PaymentDTO(
                        row[0].trim(),
                        Double.parseDouble(row[1].trim()),
                        row[2].trim(),
                        row[3].trim()
                );
				payments.add(dto);
			}
		}
		return payments;
    }
    
}