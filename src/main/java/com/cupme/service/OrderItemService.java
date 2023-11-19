package com.cupme.service;

import com.cupme.repository.OrderItemRepository;
import com.cupme.service.dto.OrderItemDTO;
import com.cupme.service.mapper.OrderItemMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing order Items.
 */
@Service
@Transactional
public class OrderItemService {

    private final Logger log = LoggerFactory.getLogger(OrderItemService.class);

    private final OrderItemRepository orderItemRepository;

    private final OrderItemMapper orderItemMapper;
    private final CacheManager cacheManager;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, CacheManager cacheManager) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.cacheManager = cacheManager;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItemMapper.orderItemsToOrderItemDTOs(orderItemRepository.findAll());
    }

    public OrderItemDTO getOrderItem(long id) {
        return orderItemMapper.orderItemToOrderItemDTO(orderItemRepository.findById(id).get());
    }

    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        return orderItemMapper.orderItemToOrderItemDTO(orderItemRepository.save(orderItemMapper.orderItemDTOToOrderItem(orderItemDTO)));
    }

    public OrderItemDTO updateOrderItem(OrderItemDTO orderItemDTO) {
        return orderItemMapper.orderItemToOrderItemDTO(orderItemRepository.save(orderItemMapper.orderItemDTOToOrderItem(orderItemDTO)));
    }

    public void deleteOrderItem(long id) {
        orderItemRepository.deleteById(id);
    }
}
