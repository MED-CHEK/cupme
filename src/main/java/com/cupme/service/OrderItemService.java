package com.cupme.service;

import com.cupme.domain.Order;
import com.cupme.domain.OrderItem;
import com.cupme.domain.User;
import com.cupme.domain.enumeration.ProductType;
import com.cupme.repository.OrderItemRepository;
import com.cupme.repository.OrderRepository;
import com.cupme.service.dto.*;
import com.cupme.service.mapper.OrderItemMapper;
import com.cupme.service.mapper.OrderMapper;
import com.cupme.service.mapper.ProductMapper;
import com.cupme.service.mapper.ProtocolMapper;
import com.cupme.service.utils.AssetFilesService;
import java.time.Instant;
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
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProtocolMapper protocolMapper;
    private final ProductMapper productMapper;
    private final UserService userService;
    private final ProtocolService protocolService;
    private final ProductService productService;
    private final AppointmentService appointmentService;
    private final SessionService sessionService;
    private final CacheManager cacheManager;
    private final AssetFilesService assetFilesService;

    public OrderItemService(
        OrderItemRepository orderItemRepository,
        OrderRepository orderRepository,
        OrderMapper orderMapper,
        OrderItemMapper orderItemMapper,
        ProtocolMapper protocolMapper,
        ProductMapper productMapper,
        UserService userService,
        ProtocolService protocolService,
        ProductService productService,
        AppointmentService appointmentService,
        SessionService sessionService,
        CacheManager cacheManager,
        AssetFilesService assetFilesService
    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.protocolMapper = protocolMapper;
        this.productMapper = productMapper;
        this.userService = userService;
        this.protocolService = protocolService;
        this.productService = productService;
        this.appointmentService = appointmentService;
        this.sessionService = sessionService;
        this.cacheManager = cacheManager;
        this.assetFilesService = assetFilesService;
    }

    public boolean hasProtocols() {
        User user = userService.getUserWithAuthorities().get();
        return orderItemRepository.existsByOrderUserIdAndProtocolIsNotNull(user.getId());
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
                return protocolCartDTO;
            })
            .collect(Collectors.toList());
    }

    public OrderItemDTO getOrderItem(long id) {
        return orderItemMapper.orderItemToOrderItemDTO(orderItemRepository.findById(id).get());
    }

    public List<OrderItemDTO> getOrderItemsByOrderId(long id) {
        return orderItemMapper.orderItemsToOrderItemDTOs(orderItemRepository.findAllByOrderId(id));
    }

    public Long createOrderItem(OrderServerDTO orderServerDTO) {
        User user = null;
        if (orderServerDTO.getUserId() == null) {
            user = userService.getUserWithAuthorities().get();
        } else {
            user = userService.getUserWithAuthoritiesById(orderServerDTO.getUserId()).get();
        }
        Order order = new Order();
        order.setUser(user);
        order.setPaid(orderServerDTO.isPaid());
        order.setTotalPrice(orderServerDTO.getTotalPrice());
        order.setTransactionId(orderServerDTO.getTransactionId());

        Order finalOrder = orderRepository.save(order);
        orderServerDTO
            .getOrderItemServerDTOs()
            .forEach(orderItemServerDTO -> {
                if (orderItemServerDTO.getType() == ProductType.SESSION) {
                    SessionDTO sessionDTO = sessionService.getSession(orderItemServerDTO.getProductId());
                    AppointmentDTO appointmentDTO = new AppointmentDTO();
                    appointmentDTO.setEmail(orderItemServerDTO.getAppointmentInfo().getEmail());
                    appointmentDTO.setTelephone(orderItemServerDTO.getAppointmentInfo().getTelephone());
                    appointmentDTO.setType(orderItemServerDTO.getAppointmentInfo().getType());
                    appointmentDTO.setOrder(orderMapper.orderToOrderDTO(finalOrder));
                    appointmentDTO.setAppointmentDate(orderItemServerDTO.getAppointmentInfo().getAppointmentDate());
                    appointmentDTO.setAppointmentTime(orderItemServerDTO.getAppointmentInfo().getAppointmentTime());
                    appointmentDTO.setSession(sessionDTO);
                    appointmentService.createAppointment(appointmentDTO);
                } else {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setOrder(orderMapper.orderToOrderDTO(finalOrder));
                    orderItemDTO.setQuantity(orderItemServerDTO.getQuantity());
                    if (orderItemServerDTO.getType() == ProductType.PROTOCOL) {
                        orderItemDTO.setProtocol(
                            protocolMapper.protocolDTOToProtocolCartDTO(protocolService.getProtocol(orderItemServerDTO.getProductId()))
                        );
                    } else if (orderItemServerDTO.getType() == ProductType.PRODUCT) {
                        ProductDTO productDTO = productService.getProduct(orderItemServerDTO.getProductId());
                        productDTO.setStock(productDTO.getStock() - orderItemServerDTO.getQuantity());
                        productService.updateProduct(productDTO);
                        orderItemDTO.setProduct(productMapper.productDTOToProductCartDTO(productDTO));
                    }
                    orderItemRepository.save(orderItemMapper.orderItemDTOToOrderItem(orderItemDTO));
                }
            });

        return finalOrder.getId();
    }

    public Long updateOrderItem(OrderServerDTO orderServerDTO) {
        User user = null;
        Order order = orderRepository.findById(orderServerDTO.getOrderId()).get();

        if (orderServerDTO.getUserId() != null && orderServerDTO.getUserId() != order.getUser().getId()) {
            user = userService.getUserWithAuthoritiesById(orderServerDTO.getUserId()).get();
        } else if (orderServerDTO.getUserId() == null) {
            user = order.getUser();
        } else {
            user = userService.getUserWithAuthorities().get();
        }

        order.setUser(user);
        order.setPaid(orderServerDTO.isPaid());
        order.setTotalPrice(orderServerDTO.getTotalPrice());
        order.setTransactionId(orderServerDTO.getTransactionId());
        order.setCreatedDate(Instant.now());

        orderRepository.save(order);
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
        List<OrderItemServerDTO> orderItemServerDTOs = orderServerDTO.getOrderItemServerDTOs();

        orderItems.removeIf(orderItem ->
            orderItemServerDTOs
                .stream()
                .noneMatch(serverDTO ->
                    (orderItem.getProduct() != null && serverDTO.getProductId().equals(orderItem.getProduct().getId())) ||
                    (orderItem.getProtocol() != null && serverDTO.getProductId().equals(orderItem.getProtocol().getId()))
                )
        );

        orderItemRepository.deleteByOrderId(order.getId());

        orderItems =
            orderItems
                .stream()
                .peek(orderItem ->
                    orderItemServerDTOs
                        .stream()
                        .filter(serverDTO ->
                            (orderItem.getProduct() != null && serverDTO.getProductId().equals(orderItem.getProduct().getId())) ||
                            (orderItem.getProtocol() != null && serverDTO.getProductId().equals(orderItem.getProtocol().getId()))
                        )
                        .findFirst()
                        .ifPresent(serverDTO -> {
                            int quantity = orderItem.getQuantity();
                            orderItem.setQuantity(serverDTO.getQuantity());
                            if (serverDTO.getType() == ProductType.PROTOCOL) {
                                orderItem.setProtocol(
                                    protocolMapper.protocolDTOToProtocol(protocolService.getProtocol(serverDTO.getProductId()))
                                );
                            } else {
                                ProductDTO productDTO = productService.getProduct(serverDTO.getProductId());
                                productDTO.setStock(productDTO.getStock() + quantity - serverDTO.getQuantity());
                                productService.updateProduct(productDTO);
                                orderItem.setProduct(productMapper.productDTOToProduct(productDTO));
                            }
                            orderItemServerDTOs.remove(serverDTO);
                        })
                )
                .collect(Collectors.toList());

        List<OrderItem> remainingItemsToAdd = orderItemServerDTOs
            .stream()
            .map(serverDTO -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setOrder(orderMapper.orderToOrderDTO(order));
                orderItemDTO.setQuantity(serverDTO.getQuantity());
                if (serverDTO.getType() == ProductType.PROTOCOL) {
                    orderItemDTO.setProtocol(
                        protocolMapper.protocolDTOToProtocolCartDTO(protocolService.getProtocol(serverDTO.getProductId()))
                    );
                } else {
                    ProductDTO productDTO = productService.getProduct(serverDTO.getProductId());
                    productDTO.setStock(productDTO.getStock() - serverDTO.getQuantity());
                    productService.updateProduct(productDTO);
                    orderItemDTO.setProduct(productMapper.productDTOToProductCartDTO(productDTO));
                }
                return orderItemMapper.orderItemDTOToOrderItem(orderItemDTO);
            })
            .collect(Collectors.toList());

        orderItems.addAll(remainingItemsToAdd);
        orderItems.stream().forEach(orderItem -> orderItemRepository.save(orderItem));
        return order.getId();
    }

    public void deleteOrderItem(Long orderId) {
        orderItemRepository.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }
}
