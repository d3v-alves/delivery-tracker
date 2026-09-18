package br.com.d3valves.deliverytracker.model;

import java.time.LocalDate;
import java.util.Objects;

public class Delivery {

    private Long id;
    private String recipient;
    private String address;
    private DeliveryStatus status;
    private final LocalDate createdDate;
    private LocalDate expectedDate;
    private LocalDate deliveredDate;

    public Delivery(Long id, String recipient, String address, LocalDate expectedDate) {
        this.id = id;
        this.recipient = recipient;
        this.address = address;
        this.expectedDate = expectedDate;
        this.createdDate = LocalDate.now();
        this.status = DeliveryStatus.PENDING;
    }

    public boolean isLate() {
        return status == DeliveryStatus.IN_TRANSIT
                && expectedDate != null
                && expectedDate.isBefore(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Delivery delivery)) return false;
        return Objects.equals(id, delivery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}