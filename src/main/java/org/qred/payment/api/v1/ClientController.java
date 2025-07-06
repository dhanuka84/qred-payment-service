package org.qred.payment.api.v1;

import java.util.List;

import org.qred.payment.domain.ClientDTO;
import org.qred.payment.service.ClientService;
import org.qred.payment.validator.ClientValidator;
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

/**
 * @author : Dhanuka Ranasinghe
 * @since : Date: 05/07/2025
 */
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientValidator validator;

    public ClientController(ClientService clientService, ClientValidator validator) {
        this.clientService = clientService;
        this.validator = validator;
    }

    @Operation(summary = "Get all clients.", description = "Fetch all client records.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Get a client by ID.", description = "Fetch a single client by ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval."),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Create client entity.", description = "Create client entity.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO client) {
        validator.validateClientNameRequest(client.clientName());
        ClientDTO savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @Operation(summary = "Update a client.", description = "Update an existing client.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated."),
        @ApiResponse(responseCode = "400", description = "Bad Request."),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO client) {
        validator.validateClientNameRequest(client.clientName());
        ClientDTO updatedClient = clientService.update(id, client);
        return ResponseEntity.ok(updatedClient);
    }
}
