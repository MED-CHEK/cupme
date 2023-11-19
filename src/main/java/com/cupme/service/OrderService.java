package com.cupme.service;

import com.cupme.repository.OrderRepository;
import com.cupme.service.dto.OrderDTO;
import com.cupme.service.mapper.OrderMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing orders.
 */
@Service
@Transactional
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;
    private final CacheManager cacheManager;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CacheManager cacheManager) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cacheManager = cacheManager;
    }

    public List<OrderDTO> getOrders() {
        return orderMapper.ordersToOrderDTOs(orderRepository.findAll());
    }

    public OrderDTO getOrder(long id) {
        return orderMapper.orderToOrderDTO(orderRepository.findById(id).get());
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        return orderMapper.orderToOrderDTO(orderRepository.save(orderMapper.orderDTOToOrder(orderDTO)));
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        return orderMapper.orderToOrderDTO(orderRepository.save(orderMapper.orderDTOToOrder(orderDTO)));
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
