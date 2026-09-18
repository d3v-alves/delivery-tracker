package br.com.d3valves.deliverytracker.repository;

import br.com.d3valves.deliverytracker.model.Delivery;
import br.com.d3valves.deliverytracker.model.DeliveryStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class DeliveryRepositoryMemory implements DeliveryRepository {

    private final Map<Long, Delivery> data = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Long nextId() {
        return idGenerator.getAndIncrement();
    }

    @Override
    public Delivery save(Delivery delivery) {
        data.put(delivery.getId(), delivery);
        return delivery;
    }

    @Override
    public Optional<Delivery> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Delivery> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<Delivery> findByStatus(DeliveryStatus status) {
        return data.values().stream()
                .filter(delivery -> delivery.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return data.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        data.remove(id);
    }
}