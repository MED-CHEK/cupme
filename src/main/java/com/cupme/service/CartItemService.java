package com.cupme.service;

import com.cupme.domain.Cart;
import com.cupme.domain.CartItem;
import com.cupme.domain.enumeration.ProductType;
import com.cupme.repository.CartItemRepository;
import com.cupme.service.dto.CartItemDTO;
import com.cupme.service.dto.CartItemDisplayDTO;
import com.cupme.service.dto.CartItemSessionDTO;
import com.cupme.service.dto.PictureDTO;
import com.cupme.service.mapper.CartItemMapper;
import com.cupme.service.mapper.CartMapper;
import com.cupme.service.mapper.ProductMapper;
import com.cupme.service.mapper.ProtocolMapper;
import com.cupme.service.utils.AssetFilesService;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing cart items.
 */
@Service
@Transactional
public class CartItemService {

    private final Logger log = LoggerFactory.getLogger(CartItemService.class);

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    private final CartMapper cartMapper;
    private final CacheManager cacheManager;

    private final UserService userService;

    private final CartService cartservice;

    private final ProtocolService protocolService;

    private final ProtocolMapper protocolMapper;

    private final ProductService productService;

    private final ProductMapper productMapper;

    private final AssetFilesService assetFilesService;

    public CartItemService(
        CartItemRepository cartItemRepository,
        CartItemMapper cartItemMapper,
        CartMapper cartMapper,
        CacheManager cacheManager,
        UserService userService,
        CartService cartservice,
        ProtocolService protocolService,
        ProtocolMapper protocolMapper,
        ProductService productService,
        ProductMapper productMapper,
        AssetFilesService assetFilesService
    ) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartMapper = cartMapper;
        this.cacheManager = cacheManager;
        this.userService = userService;
        this.cartservice = cartservice;
        this.protocolService = protocolService;
        this.protocolMapper = protocolMapper;
        this.productService = productService;
        this.productMapper = productMapper;
        this.assetFilesService = assetFilesService;
    }

    public Integer getCartSize() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        return cartItemRepository.countAllByCartUserId(userId);
    }

    public List<CartItemDTO> getCartItems() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        List<CartItemDTO> cartItems = cartItemMapper.cartItemsToCartItemDTOs(cartItemRepository.findAllByCartUserId(userId));
        cartItems
            .stream()
            .forEach(cartItemDTO -> {
                if (cartItemDTO.getProtocolCartDTO() != null) {
                    PictureDTO pictureDTO = cartItemDTO.getProtocolCartDTO().getPicture();
                    pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
                    pictureDTO.setProtocol(null);
                    cartItemDTO.getProtocolCartDTO().setPicture(pictureDTO);
                } else if (cartItemDTO.getProductCartDTO() != null) {
                    PictureDTO pictureDTO = cartItemDTO.getProductCartDTO().getPicture();
                    pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
                    pictureDTO.setProduct(null);
                    cartItemDTO.getProductCartDTO().setPicture(pictureDTO);
                }
            });
        return cartItems;
    }

    public CartItemDTO getCartItem() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        return cartItemMapper.cartItemToCartItemDTO(cartItemRepository.findByCartUserId(userId));
    }

    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = null;
        if (cartItemDTO.getProductCartDTO() != null) {
            cartItem =
                cartItemRepository.findByCartIdAndProductId(cartItemDTO.getCartDTO().getId(), cartItemDTO.getProductCartDTO().getId());
        } else if (cartItemDTO.getProtocolCartDTO() != null) {
            cartItem =
                cartItemRepository.findByCartIdAndProtocolId(cartItemDTO.getCartDTO().getId(), cartItemDTO.getProtocolCartDTO().getId());
        }

        if (cartItem == null) {
            cartItemDTO.setCreatedDate(Instant.now());
            return cartItemMapper.cartItemToCartItemDTO(cartItemRepository.save(cartItemMapper.cartItemDTOToCartItem(cartItemDTO)));
        }
        return null;
    }

    public CartItemDTO updateCartItem(CartItemDTO cartItemDTO) {
        CartItemDTO cartItem = cartItemMapper.cartItemToCartItemDTO(
            cartItemRepository.save(cartItemMapper.cartItemDTOToCartItem(cartItemDTO))
        );

        if (cartItem.getProtocolCartDTO() != null) {
            PictureDTO pictureDTO = cartItem.getProtocolCartDTO().getPicture();
            pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
            pictureDTO.setProtocol(null);
            cartItem.getProtocolCartDTO().setPicture(pictureDTO);
        } else if (cartItem.getProductCartDTO() != null) {
            PictureDTO pictureDTO = cartItem.getProductCartDTO().getPicture();
            pictureDTO.setFile(assetFilesService.getFile(pictureDTO.getFile()));
            pictureDTO.setProduct(null);
            cartItem.getProductCartDTO().setPicture(pictureDTO);
        }

        return cartItem;
    }

    public void deleteCartItem(long id) {
        cartItemRepository.deleteById(id);
    }

    public void deleteCartItems(Long cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
    }

    public void persistCartItems(List<CartItemSessionDTO> cartItemSessionDTOs) {
        Cart cart = cartMapper.cartDTOToCart(cartservice.getCartForUser());
        cartItemSessionDTOs
            .stream()
            .forEach(cartItemSessionDTO -> {
                CartItem cartItem = null;
                if (cartItemSessionDTO.getType() == ProductType.PROTOCOL) {
                    cartItem = cartItemRepository.findByCartIdAndProtocolId(cart.getId(), cartItemSessionDTO.getProductId());
                } else {
                    cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), cartItemSessionDTO.getProductId());
                }
                if (cartItem == null) {
                    cartItem = new CartItem();
                    cartItem.setCart(cart);
                    if (cartItemSessionDTO.getType() == ProductType.PROTOCOL) {
                        cartItem.setProtocol(
                            protocolMapper.protocolDTOToProtocol(protocolService.getProtocol(cartItemSessionDTO.getProductId()))
                        );
                    } else {
                        cartItem.setProduct(
                            productMapper.productDTOToProduct(productService.getProduct(cartItemSessionDTO.getProductId()))
                        );
                    }
                    cartItem.setQuantity(cartItemSessionDTO.getQuantity());
                    cartItem.setCreatedDate(cartItemSessionDTO.getCreatedDate());
                    cartItemRepository.save(cartItem);
                } else {
                    cartItem.setQuantity(cartItemSessionDTO.getQuantity());
                    cartItemRepository.save(cartItem);
                }
            });
    }

    public List<CartItemDisplayDTO> getPersistedCartItems() {
        Long userId = userService.getUserWithAuthorities().get().getId();
        List<CartItemDisplayDTO> cartItems = cartItemMapper.cartItemsToCartItemDisplayDTOs(cartItemRepository.findAllByCartUserId(userId));

        return cartItems;
    }
}
