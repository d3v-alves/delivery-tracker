package br.com.d3valves.deliverytracker.repository;

import br.com.d3valves.deliverytracker.model.Delivery;
import br.com.d3valves.deliverytracker.model.DeliveryStatus;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    Long nextId();

    Delivery save(Delivery delivery);

    Optional<Delivery> findById(Long id);

    List<Delivery> findAll();

    List<Delivery> findByStatus(DeliveryStatus status);

    boolean existsById(Long id);

    void deleteById(Long id);
}