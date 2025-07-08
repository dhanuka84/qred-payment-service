package org.qred.payment.api.v1;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.qred.payment.domain.PaymentDTO;
import org.qred.payment.service.PaymentService;
import org.qred.payment.validator.PaymentValidator;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PaymentValidator validator;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void shouldReturnAllPayments() throws Exception {
        List<PaymentDTO> payments = List.of(
                new PaymentDTO("2024-01-01", 100.0, "incoming", "C123"),
                new PaymentDTO("2024-01-02", 200.0, "outgoing", "C456")
        );

        when(paymentService.findAll()).thenReturn(payments);

        mockMvc.perform(get("/api/v1/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].contractNumber").value("C123"));
    }

    @Test
    void shouldReturnPaymentById() throws Exception {
        PaymentDTO dto = new PaymentDTO("2024-01-01", 150.0, "incoming", "C789");

        when(paymentService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/payments/1"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.amount").value(150.0))
                .andExpect(jsonPath("$.contractNumber").value("C789"));
    }

    @Test
    void shouldCreatePayment() throws Exception {
        PaymentDTO request = new PaymentDTO("2024-01-03", 300.0, "incoming", "C999");
        String requestJson = """
            {
              "paymentDate": "2024-01-03",
              "amount": 300.0,
              "type": "incoming",
              "contractNumber": "C999"
            }
        """;

        when(paymentService.save(any(PaymentDTO.class))).thenReturn(request);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(300.0))
                .andExpect(jsonPath("$.contractNumber").value("C999"));
    }

    @Test
    void shouldUpdatePayment() throws Exception {
        PaymentDTO request = new PaymentDTO("2024-01-04", 400.0, "outgoing", "C321");
        PaymentDTO updated = new PaymentDTO("2024-01-04", 400.0, "outgoing", "C321");

        String requestJson = """
            {
              "paymentDate": "2024-01-04",
              "amount": 400.0,
              "type": "outgoing",
              "contractNumber": "C321"
            }
        """;

        when(paymentService.update(eq(1L), any(PaymentDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(400.0))
                .andExpect(jsonPath("$.type").value("outgoing"));
    }

    @Test
    void shouldUploadCsvSuccessfully() throws Exception {
        String csvContent = """
            payment_date,amount,type,contract_number
            2024-01-10,1000.00,incoming,C001
            2024-01-11,500.00,outgoing,C002
        """;

        MockMultipartFile file = new MockMultipartFile(
                "file", "payments.csv", "text/csv", csvContent.getBytes()
        );

        mockMvc.perform(multipart("/api/v1/payments/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.message").value("Successfully processed payments"));
    }

    @Test
    void shouldReturnBadRequestForEmptyUpload() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.csv", "text/csv", new byte[0]);

        mockMvc.perform(multipart("/api/v1/payments/upload").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Empty file."));
    }
}
