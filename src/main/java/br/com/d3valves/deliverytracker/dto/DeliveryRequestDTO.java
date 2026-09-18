package br.com.d3valves.deliverytracker.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DeliveryRequestDTO(

        @NotBlank(message = "recipient is required")
        String recipient,

        @NotBlank(message = "address is required")
        String address,

        @NotNull(message = "expectedDate is required")
        @FutureOrPresent(message = "expectedDate cannot be in the past")
        LocalDate expectedDate
) {
}