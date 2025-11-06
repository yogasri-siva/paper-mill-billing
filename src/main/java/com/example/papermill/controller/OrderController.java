package com.example.papermill.controller;

import com.example.papermill.dto.CreateOrderRequest;
import com.example.papermill.dto.OrderResponse;
import com.example.papermill.entity.PaperOrder;
import com.example.papermill.repository.PaperOrderRepository;
import com.example.papermill.service.BillingService;
import com.example.papermill.service.OrderEventPublisher;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * REST controller exposing CRUD endpoints for orders.  POST requests trigger
 * pricing logic and persist the resulting order.  GET endpoints return
 * order data.  DELETE allows removal of unwanted orders.
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin // permit cross‑origin requests from the static front-end
public class OrderController {

    private final PaperOrderRepository repository;
    private final BillingService billingService;
    private final OrderEventPublisher eventPublisher;

    public OrderController(PaperOrderRepository repository,
                           BillingService billingService,
                           OrderEventPublisher eventPublisher) {
        this.repository = repository;
        this.billingService = billingService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Create a new order.  Applies pricing rules and stores the order in
     * the database.  Returns a breakdown of the bill to the client.
     */
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        var bill = billingService.computeBill(
                request.getQuantity(),
                request.getWidth(),
                request.getGsm(),
                request.getBf(),
                request.getCobb()
        );

        PaperOrder order = new PaperOrder();
        order.setQuantity(request.getQuantity());
        order.setWidth(request.getWidth());
        order.setGsm(request.getGsm());
        order.setBf(request.getBf());
        order.setCobb(request.getCobb());
        order.setPrice(bill.total);
        order.setTimestamp(Instant.now());
        PaperOrder saved = repository.save(order);

        // Publish a mock event for the new order
        String payload = String.format(
                "{\"orderId\":%d,\"quantity\":%d,\"width\":%.2f,\"gsm\":%d,\"bf\":%d,\"total\":%s}",
                saved.getId(), saved.getQuantity(), saved.getWidth(), saved.getGsm(), saved.getBf(), bill.total.toPlainString()
        );
        eventPublisher.publishOrderCreated(saved.getId(), payload);

        return ResponseEntity.ok(new OrderResponse(
                saved.getId(),
                bill.pricePerUnit,
                bill.subtotal,
                bill.gst,
                bill.total
        ));
    }

    /**
     * Retrieve all orders.  Returns a list of PaperOrder entities.  The
     * response will include the entire entity (including price and timestamp).
     */
    @GetMapping
    public List<PaperOrder> list() {
        return repository.findAll();
    }

    /**
     * Fetch a single order by ID.  If the order does not exist a 404
     * response is returned.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaperOrder> get(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete an order by ID.  Returns 204 No Content on success or 404 if
     * the ID does not correspond to an existing order.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}