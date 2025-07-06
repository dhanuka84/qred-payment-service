package org.qred.payment.api.v1;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import java.util.List;

import org.qred.payment.domain.ContractDTO;
import org.qred.payment.service.ContractService;
import org.qred.payment.validator.RestValidator;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    private final ContractService contractService;
    private final RestValidator validator;

    public ContractController(ContractService contractService, RestValidator validator) {
        this.contractService = contractService;
        this.validator = validator;
    }

    @Operation(summary = "Get all contracts.", description = "Fetch all contract records.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<ContractDTO> getAllContracts() {
        return contractService.findAll();
    }

    @Operation(summary = "Get a contract by ID.", description = "Fetch a single contract by ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "404", description = "Contract not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ContractDTO getContractById(@PathVariable Long id) {
        return contractService.findById(id);
    }

    @Operation(summary = "Create contract entity.", description = "Create a new contract.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ContractDTO createContract(@RequestBody ContractDTO contract) {
        validator.validateCreate(contract);
        return contractService.save(contract);
    }

    @Operation(summary = "Update a contract.", description = "Update an existing contract.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "404", description = "Contract not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ContractDTO updateContract(@PathVariable Long id, @RequestBody ContractDTO contract) {
        validator.validateUpdateRequest(id, contract);
        return contractService.update(id, contract);
    }
}
