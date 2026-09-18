package br.com.d3valves.deliverytracker.exception;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(Long id) {
        super("Delivery not found with id: " + id);
    }
}