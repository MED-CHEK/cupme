package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.OrderItemService;
import com.cupme.service.dto.OrderItemDTO;
import com.cupme.service.dto.OrderServerDTO;
import com.cupme.service.dto.ProtocolCartDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderItemResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemResource.class);

    private final OrderItemService orderItemService;

    public OrderItemResource(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    /**
     * {@code GET /orderItems} : get all orderItems with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all orderItemes.
     */
    @GetMapping("/orderItems")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItemes() {
        log.debug("REST request to get all public OrderItem names");

        final List<OrderItemDTO> orderItemes = orderItemService.getOrderItems();
        return ResponseEntity.ok().body(orderItemes);
    }

    /**
     * {@code GET /orderProtocols} : get all orderProtocols with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all orderItemes.
     */
    @GetMapping("/orderProtocols")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<ProtocolCartDTO>> getAllOrderProtocols() {
        log.debug("REST request to get all public OrderItem names");

        final List<ProtocolCartDTO> orderProtocols = orderItemService.getOrderProtocols();
        return ResponseEntity.ok().body(orderProtocols);
    }

    /**
     * {@code GET /orderItems/:id} : get the "id" orderItem.
     * @param id the id of the orderItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orderItems/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<OrderItemDTO> getOrderItem(long id) {
        log.debug("REST request to get OrderItem : {}", id);

        final OrderItemDTO orderItem = orderItemService.getOrderItem(id);
        return ResponseEntity.ok().body(orderItem);
    }

    /**
     * {@code POST  /orderItems} : Create a new orderItem.
     *
     * @param orderServerDTO the orderItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderItemDTO, or with status {@code 400 (Bad Request)} if the orderItem has already an ID.
     */
    @PostMapping("/orderItems")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Long> createOrderItem(@Valid @RequestBody OrderServerDTO orderServerDTO) {
        log.debug("REST request to save orderServerDTO : {}", orderServerDTO);

        final Long orderId = orderItemService.createOrderItem(orderServerDTO);
        return ResponseEntity.ok().body(orderId);
    }
}
