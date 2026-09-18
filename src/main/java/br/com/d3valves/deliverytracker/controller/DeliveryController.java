package br.com.d3valves.deliverytracker.controller;

import br.com.d3valves.deliverytracker.dto.DeliveryRequestDTO;
import br.com.d3valves.deliverytracker.dto.DeliveryResponseDTO;
import br.com.d3valves.deliverytracker.dto.UpdateStatusRequestDTO;
import br.com.d3valves.deliverytracker.model.Delivery;
import br.com.d3valves.deliverytracker.model.DeliveryStatus;
import br.com.d3valves.deliverytracker.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> create(@Valid @RequestBody DeliveryRequestDTO request) {
        Delivery created = service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DeliveryResponseDTO.fromEntity(created));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponseDTO>> findAll(
            @RequestParam(required = false) DeliveryStatus status) {

        List<Delivery> deliveries = (status != null)
                ? service.findByStatus(status)
                : service.findAll();

        List<DeliveryResponseDTO> response = deliveries.stream()
                .map(DeliveryResponseDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> findById(@PathVariable Long id) {
        Delivery delivery = service.findById(id);
        return ResponseEntity.ok(DeliveryResponseDTO.fromEntity(delivery));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequestDTO request) {

        Delivery updated = service.updateStatus(id, request.status());
        return ResponseEntity.ok(DeliveryResponseDTO.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}