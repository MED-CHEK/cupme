package com.cupme.service;

import com.cupme.domain.Order;
import com.cupme.domain.User;
import com.cupme.domain.enumeration.ProductType;
import com.cupme.repository.OrderItemRepository;
import com.cupme.repository.OrderRepository;
import com.cupme.service.dto.OrderItemDTO;
import com.cupme.service.dto.OrderServerDTO;
import com.cupme.service.dto.ProtocolCartDTO;
import com.cupme.service.mapper.OrderItemMapper;
import com.cupme.service.mapper.ProductMapper;
import com.cupme.service.mapper.ProtocolMapper;
import com.cupme.service.utils.AssetFilesService;
import java.util.List;
import java.util.stream.Collectors;
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
    private final OrderRepository orderRepository;

    private final OrderItemMapper orderItemMapper;
    private final ProtocolMapper protocolMapper;
    private final ProductMapper productMapper;

    private final UserService userService;
    private final ProtocolService protocolService;
    private final ProductService productService;
    private final CacheManager cacheManager;
    private final AssetFilesService assetFilesService;

    public OrderItemService(
        OrderItemRepository orderItemRepository,
        OrderRepository orderRepository,
        OrderItemMapper orderItemMapper,
        ProtocolMapper protocolMapper,
        ProductMapper productMapper,
        UserService userService,
        ProtocolService protocolService,
        ProductService productService,
        CacheManager cacheManager,
        AssetFilesService assetFilesService
    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemMapper = orderItemMapper;
        this.protocolMapper = protocolMapper;
        this.productMapper = productMapper;
        this.userService = userService;
        this.protocolService = protocolService;
        this.productService = productService;
        this.cacheManager = cacheManager;
        this.assetFilesService = assetFilesService;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItemMapper.orderItemsToOrderItemDTOs(orderItemRepository.findAll());
    }

    public List<ProtocolCartDTO> getOrderProtocols() {
        User user = userService.getUserWithAuthorities().get();
        return orderItemRepository
            .findByOrderUserId(user.getId())
            .stream()
            .filter(orderItem -> orderItem.getProtocol() != null)
            .map(orderItem -> {
                ProtocolCartDTO protocolCartDTO = protocolMapper.protocolToProtocolCartDTO(orderItem.getProtocol());
                protocolCartDTO.getPicture().setFile(assetFilesService.getFile(protocolCartDTO.getPicture().getFile()));
                return protocolCartDTO;
            })
            .collect(Collectors.toList());
    }

    public OrderItemDTO getOrderItem(long id) {
        return orderItemMapper.orderItemToOrderItemDTO(orderItemRepository.findById(id).get());
    }

    public Long createOrderItem(OrderServerDTO orderServerDTO) {
        User user = userService.getUserWithAuthorities().get();
        Order order = new Order();
        order.setUser(user);
        order.setPaid(true);
        order.setTotalPrice(orderServerDTO.getTotalPrice());
        order.setTransactionId(orderServerDTO.getTransactionId());

        Order finalOrder = orderRepository.save(order);
        orderServerDTO
            .getOrderItemServerDTOs()
            .forEach(orderItemServerDTO -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setOrder(finalOrder);
                orderItemDTO.setQuantity(orderItemServerDTO.getQuantity());
                if (orderItemServerDTO.getType() == ProductType.PROTOCOL) {
                    orderItemDTO.setProtocol(
                        protocolMapper.protocolDTOToProtocol(protocolService.getProtocol(orderItemServerDTO.getProductId()))
                    );
                } else {
                    orderItemDTO.setProduct(
                        productMapper.productDTOToProduct(productService.getProduct(orderItemServerDTO.getProductId()))
                    );
                }
                orderItemRepository.save(orderItemMapper.orderItemDTOToOrderItem(orderItemDTO));
            });

        return finalOrder.getId();
    }
}
