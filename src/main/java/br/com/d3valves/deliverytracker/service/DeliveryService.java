package br.com.d3valves.deliverytracker.service;

import br.com.d3valves.deliverytracker.dto.DeliveryRequestDTO;
import br.com.d3valves.deliverytracker.model.Delivery;
import br.com.d3valves.deliverytracker.model.DeliveryStatus;

import java.util.List;

public interface DeliveryService {

    Delivery create(DeliveryRequestDTO request);

    Delivery updateStatus(Long id, DeliveryStatus newStatus);

    Delivery findById(Long id);

    List<Delivery> findAll();

    List<Delivery> findByStatus(DeliveryStatus status);

    void delete(Long id);
}