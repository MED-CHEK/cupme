package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.OrderService;
import com.cupme.service.dto.OrderDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * {@code GET /orders} : get all orderes with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all orderes.
     */
    @GetMapping("/orders")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<OrderDTO>> getAllOrderes() {
        log.debug("REST request to get all public Order names");

        final List<OrderDTO> orderes = orderService.getOrders();
        return ResponseEntity.ok().body(orderes);
    }

    /**
     * {@code GET /orders/:id} : get the "id" order.
     * @param id the id of the orderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<OrderDTO> getOrder(long id) {
        log.debug("REST request to get Order : {}", id);

        final OrderDTO order = orderService.getOrder(id);
        return ResponseEntity.ok().body(order);
    }

    /**
     * {@code POST  /orders} : Create a new order.
     *
     * @param orderDTO the orderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDTO, or with status {@code 400 (Bad Request)} if the order has already an ID.
     */
    @PostMapping("/orders")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("REST request to save Order : {}", orderDTO);

        if (orderDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final OrderDTO result = orderService.createOrder(orderDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /orders} : Updates an existing order.
     *
     * @param orderDTO the orderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     */
    @PutMapping("/orders")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("REST request to update Order : {}", orderDTO);

        if (orderDTO.getId() == null) {
            return createOrder(orderDTO);
        }

        final OrderDTO result = orderService.updateOrder(orderDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" order.
     *
     * @param id the id of the orderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orders/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteOrder(long id) {
        log.debug("REST request to delete Order : {}", id);

        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
