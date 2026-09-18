package br.com.d3valves.deliverytracker.service;

import br.com.d3valves.deliverytracker.dto.DeliveryRequestDTO;
import br.com.d3valves.deliverytracker.exception.DeliveryNotFoundException;
import br.com.d3valves.deliverytracker.exception.InvalidStatusTransitionException;
import br.com.d3valves.deliverytracker.model.Delivery;
import br.com.d3valves.deliverytracker.model.DeliveryStatus;
import br.com.d3valves.deliverytracker.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final Map<DeliveryStatus, Set<DeliveryStatus>> ALLOWED_TRANSITIONS =
            new EnumMap<>(DeliveryStatus.class);

    static {
        ALLOWED_TRANSITIONS.put(DeliveryStatus.PENDING,
                EnumSet.of(DeliveryStatus.IN_TRANSIT, DeliveryStatus.CANCELLED));
        ALLOWED_TRANSITIONS.put(DeliveryStatus.IN_TRANSIT,
                EnumSet.of(DeliveryStatus.DELIVERED, DeliveryStatus.CANCELLED));
        // DELIVERED e CANCELLED: sem entrada = nenhuma transição permitida a partir deles.
    }

    private final DeliveryRepository repository;

    public DeliveryServiceImpl(DeliveryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Delivery create(DeliveryRequestDTO request) {
        Long id = repository.nextId();
        Delivery delivery = new Delivery(
                id,
                request.recipient(),
                request.address(),
                request.expectedDate()
        );
        return repository.save(delivery);
    }

    @Override
    public Delivery updateStatus(Long id, DeliveryStatus newStatus) {
        Delivery delivery = findById(id);
        DeliveryStatus currentStatus = delivery.getStatus();

        Set<DeliveryStatus> allowedNextStatuses = ALLOWED_TRANSITIONS.get(currentStatus);
        boolean isAllowed = allowedNextStatuses != null && allowedNextStatuses.contains(newStatus);

        if (!isAllowed) {
            throw new InvalidStatusTransitionException(currentStatus, newStatus);
        }

        delivery.setStatus(newStatus);
        if (newStatus == DeliveryStatus.DELIVERED) {
            delivery.setDeliveredDate(LocalDate.now());
        }

        return repository.save(delivery);
    }

    @Override
    public Delivery findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));
    }

    @Override
    public List<Delivery> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Delivery> findByStatus(DeliveryStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new DeliveryNotFoundException(id);
        }
        repository.deleteById(id);
    }
}