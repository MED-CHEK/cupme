package com.cupme.service.mapper;

import com.cupme.domain.OrderItem;
import com.cupme.service.dto.OrderItemDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link OrderItem} and its DTO called {@link OrderItem}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class OrderItemMapper {

    public List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems) {
        return orderItems.stream().filter(Objects::nonNull).map(this::orderItemToOrderItemDTO).collect(Collectors.toList());
    }

    public OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(orderItem);
    }

    public List<OrderItem> orderItemDTOsToOrderItems(List<OrderItemDTO> orderItemDTOS) {
        return orderItemDTOS.stream().filter(Objects::nonNull).map(this::orderItemDTOToOrderItem).collect(Collectors.toList());
    }

    public OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            return null;
        } else {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemDTO.getId());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setProduct(orderItemDTO.getProduct());
            orderItem.setProtocol(orderItemDTO.getProtocol());
            orderItem.setOrder(orderItemDTO.getOrder());

            return orderItem;
        }
    }
}
