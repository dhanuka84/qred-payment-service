package org.qred.payment.api.v1;

/**
 *
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */

import java.util.List;

import org.qred.payment.domain.ClientDTO;
import org.qred.payment.service.ClientService;
import org.qred.payment.validator.RestValidator;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final RestValidator validator;

    public ClientController(ClientService clientService, RestValidator validator) {
        this.clientService = clientService;
        this.validator = validator;
    }

    @Operation(summary = "Get all clients.", description = "Fetch all client records.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.findAll();
    }

    @Operation(summary = "Get a client by ID.", description = "Fetch a single client by ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @Operation(summary = "Create client entity.", description = "Create client entity.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO client) {
        validator.validateClientNameRequest(client.clientName());
        return clientService.save(client);
    }

    @Operation(summary = "Update a client.", description = "Update an existing client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Long id, @RequestBody ClientDTO client) {
        validator.validateClientNameRequest(client.clientName());
        return clientService.update(id, client);
    }
}
