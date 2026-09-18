package br.com.d3valves.deliverytracker.exception;

import br.com.d3valves.deliverytracker.model.DeliveryStatus;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(DeliveryStatus from, DeliveryStatus to) {
        super("Cannot change delivery status from " + from + " to " + to);
    }
}