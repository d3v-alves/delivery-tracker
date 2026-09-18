package br.com.d3valves.deliverytracker.dto;

import br.com.d3valves.deliverytracker.model.Delivery;
import br.com.d3valves.deliverytracker.model.DeliveryStatus;

import java.time.LocalDate;

public record DeliveryResponseDTO(
        Long id,
        String recipient,
        String address,
        DeliveryStatus status,
        LocalDate createdDate,
        LocalDate expectedDate,
        LocalDate deliveredDate,
        boolean late
) {
    public static DeliveryResponseDTO fromEntity(Delivery delivery) {
        return new DeliveryResponseDTO(
                delivery.getId(),
                delivery.getRecipient(),
                delivery.getAddress(),
                delivery.getStatus(),
                delivery.getCreatedDate(),
                delivery.getExpectedDate(),
                delivery.getDeliveredDate(),
                delivery.isLate()
        );
    }
}