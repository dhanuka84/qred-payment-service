package org.qred.payment.api.v1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.domain.PaymentDTOListWrapper;
import org.qred.payment.service.PaymentService;
import org.qred.payment.validator.PaymentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

	private final PaymentService paymentService;
	private final PaymentValidator validator;

	public PaymentController(PaymentService paymentService, PaymentValidator validator) {
		this.paymentService = paymentService;
		this.validator = validator;
	}

	@Operation(summary = "Upload payments via CSV", description = "Upload and process a CSV file to create payment entries.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "File processed successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid CSV format."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping(value = "/upload", consumes = { "multipart/form-data" })
	public ResponseEntity<?> uploadCsvFile(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Empty file."));
		}
		String filename = file.getOriginalFilename();
		List<PaymentDTO> payments = new ArrayList<>();

		try {
			if (filename != null && filename.endsWith(".csv")) {
				payments = parseCsv(file.getInputStream());
			} else if (filename != null && filename.endsWith(".xml")) {
				payments = parseXml(file.getInputStream());
			} else {
				return ResponseEntity.badRequest()
						.body(Map.of("error", "Unsupported file format. Only CSV and XML are allowed."));
			}

			paymentService.saveAsynch(payments, validator);
			return ResponseEntity.ok(Map.of("message", "Successfully processed payments", "count", payments.size()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error processing file: " + e.getMessage()));
		}
	}

	private List<PaymentDTO> parseCsv(InputStream is) throws IOException, CsvValidationException {
		List<PaymentDTO> payments = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
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

	
	private List<PaymentDTO> parseXml(InputStream is) throws Exception {
	    JAXBContext context = JAXBContext.newInstance(PaymentDTOListWrapper.class);
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    PaymentDTOListWrapper wrapper = (PaymentDTOListWrapper) unmarshaller.unmarshal(is);
	    return wrapper.getPayments();
	}
	 

	@Operation(summary = "Get all payments.", description = "Fetch all payment records.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping
	public ResponseEntity<List<PaymentDTO>> getAllPayments() {
		List<PaymentDTO> payments = paymentService.findAll();
		return ResponseEntity.ok(payments);
	}

	@Operation(summary = "Get payments by contract.", description = "Fetch payment records for contract.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping("/contracts/{contractNumber}/payments")
	public ResponseEntity<List<PaymentDTO>> findPaymentsByContractNumber(@PathVariable String contractNumber) {
		validator.validateContractNumber(contractNumber);
		List<PaymentDTO> payments = paymentService.findPaymentsByContractNumber(contractNumber);
		return ResponseEntity.status(HttpStatus.OK).body(payments);
	}

	@Operation(summary = "Get a payment by ID.", description = "Fetch a single payment by ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful retrieval."),
			@ApiResponse(responseCode = "404", description = "Payment not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@GetMapping(path="/{id}",produces = "application/json")
	public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
		PaymentDTO payment = paymentService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(payment);
	}

	@Operation(summary = "Create payment entity.", description = "Create a new payment.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successfully created."),
			@ApiResponse(responseCode = "400", description = "Bad Request."),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO payment) {
		PaymentDTO created = paymentService.save(payment);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@Operation(summary = "Update a payment.", description = "Update an existing payment.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated."),
			@ApiResponse(responseCode = "400", description = "Bad Request."),
			@ApiResponse(responseCode = "404", description = "Payment not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PutMapping(path="/{id}",consumes = "application/json", produces = "application/json")
	public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentDTO payment) {
		PaymentDTO updated = paymentService.update(id, payment);
		return ResponseEntity.ok(updated);
	}
}
