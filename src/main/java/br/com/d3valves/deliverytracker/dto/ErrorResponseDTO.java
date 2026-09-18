package br.com.d3valves.deliverytracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<String> details
) {
    public ErrorResponseDTO(int status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, List.of());
    }

    public ErrorResponseDTO(int status, String error, String message, List<String> details) {
        this(LocalDateTime.now(), status, error, message, details);
    }
}