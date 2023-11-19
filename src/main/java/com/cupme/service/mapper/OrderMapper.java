package com.cupme.service.mapper;

import com.cupme.domain.Order;
import com.cupme.service.dto.OrderDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Order} and its DTO called {@link Order}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class OrderMapper {

    public List<OrderDTO> ordersToOrderDTOs(List<Order> orders) {
        return orders.stream().filter(Objects::nonNull).map(this::orderToOrderDTO).collect(Collectors.toList());
    }

    public OrderDTO orderToOrderDTO(Order order) {
        return new OrderDTO(order);
    }

    public List<Order> orderDTOsToOrders(List<OrderDTO> orderDTOS) {
        return orderDTOS.stream().filter(Objects::nonNull).map(this::orderDTOToOrder).collect(Collectors.toList());
    }

    public Order orderDTOToOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        } else {
            Order order = new Order();
            order.setId(orderDTO.getId());
            order.setUser(orderDTO.getUser());
            order.setPaid(orderDTO.isPaid());
            order.setCreatedDate(orderDTO.getCreatedDate());

            return order;
        }
    }
}
