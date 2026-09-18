package br.com.d3valves.deliverytracker.dto;

import br.com.d3valves.deliverytracker.model.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequestDTO(

        @NotNull(message = "status is required")
        DeliveryStatus status
) {
}