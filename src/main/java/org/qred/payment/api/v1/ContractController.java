package org.qred.payment.api.v1;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import java.util.List;

import org.qred.payment.domain.ContractDTO;
import org.qred.payment.service.ContractService;
import org.qred.payment.validator.CotractValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    private final ContractService contractService;
    private final CotractValidator validator;

    public ContractController(ContractService contractService, CotractValidator validator) {
        this.contractService = contractService;
        this.validator = validator;
    }

    @Operation(summary = "Get all contracts.", description = "Fetch all contract records.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        List<ContractDTO> contracts = contractService.findAll();
        return ResponseEntity.ok(contracts);
    }

    @Operation(summary = "Get a contract by ID.", description = "Fetch a single contract by ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "404", description = "Contract not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Long id) {
        ContractDTO contract = contractService.findById(id);
        return ResponseEntity.ok(contract);
    }

    @Operation(summary = "Create contract entity.", description = "Create a new contract.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ContractDTO> createContract(@RequestBody ContractDTO contract) {
        validator.validateCreate(contract);
        ContractDTO saved = contractService.save(contract);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Update a contract.", description = "Update an existing contract.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "404", description = "Contract not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContractDTO> updateContract(@PathVariable Long id, @RequestBody ContractDTO contract) {
        validator.validateUpdateRequest(id, contract);
        ContractDTO updated = contractService.update(id, contract);
        return ResponseEntity.ok(updated);
    }
}
