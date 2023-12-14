package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.CartItemService;
import com.cupme.service.dto.CartItemDTO;
import com.cupme.service.dto.CartItemDisplayDTO;
import com.cupme.service.dto.CartItemSessionDTO;
import com.cupme.service.utils.AssetFilesService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartItemResource {

    private final Logger log = LoggerFactory.getLogger(CartItemResource.class);

    private final CartItemService cartItemService;
    private final AssetFilesService assetFilesService;

    public CartItemResource(CartItemService cartItemService, AssetFilesService assetFilesService) {
        this.cartItemService = cartItemService;
        this.assetFilesService = assetFilesService;
    }

    /**
     * {@code GET /cartItems/size} : get the size of the cart.
     * @return
     */
    @GetMapping("/cartItems/size")
    public ResponseEntity<Integer> getCartSize() {
        log.debug("REST request to get Cart size");

        final Integer size = cartItemService.getCartSize();

        return ResponseEntity.ok().body(size);
    }

    /**
     * {@code GET /cartItems} : get all cartItems with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all cartItems.
     */
    @GetMapping("/cartItems")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
        log.debug("REST request to get all public CartItem names");

        final List<CartItemDTO> cartItems = cartItemService.getCartItems();

        return ResponseEntity.ok().body(cartItems);
    }

    /**
     * {@code GET /cartItems/:id} : get the "id" cartItem.
     * @param id the id of the cartItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cartItems/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartItemDTO> getCartItem(long id) {
        log.debug("REST request to get CartItem : {}", id);

        final CartItemDTO cartItem = cartItemService.getCartItem();
        return ResponseEntity.ok().body(cartItem);
    }

    /**
     * {@code POST  /cartItems} : Create a new cartItem.
     *
     * @param cartItemDTO the cartItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartItemDTO, or with status {@code 400 (Bad Request)} if the cartItem has already an ID.
     */
    @PostMapping("/cartItems")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartItemDTO> createCartItem(@Valid @RequestBody CartItemDTO cartItemDTO) {
        log.debug("REST request to save CartItem : {}", cartItemDTO);

        if (cartItemDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final CartItemDTO result = cartItemService.createCartItem(cartItemDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /cartItems} : Updates an existing cartItem.
     *
     * @param cartItemDTO the cartItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartItemDTO,
     * or with status {@code 400 (Bad Request)} if the cartItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartItemDTO couldn't be updated.
     */
    @PutMapping("/cartItems")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartItemDTO> updateCartItem(@Valid @RequestBody CartItemDTO cartItemDTO) {
        log.debug("REST request to update CartItem : {}", cartItemDTO);

        if (cartItemDTO.getId() == null) {
            return createCartItem(cartItemDTO);
        }

        final CartItemDTO result = cartItemService.updateCartItem(cartItemDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /cartItems/:id} : delete the "id" cartItem.
     *
     * @param id the id of the cartItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cartItem/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteCartItem(@PathVariable long id) {
        log.debug("REST request to delete CartItem : {}", id);

        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code DELETE  /cartItems/all} : delete all cartItems of the connected user.
     *
     * * @param cartId the id of the cartItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cartItems/{cartId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteAllCartItems(@PathVariable long cartId) {
        log.debug("REST request to delete all CartItems");

        cartItemService.deleteCartItems(cartId);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code POST  /cartItems/persist} : persist all cartItems of the connected user.
     * @param cartItemSessionDTOs
     * @return
     */
    @PostMapping("/cartItems/persist")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> persistCartItems(@Valid @RequestBody List<CartItemSessionDTO> cartItemSessionDTOs) {
        log.debug("REST request to persist CartItems : {}", cartItemSessionDTOs);

        cartItemService.persistCartItems(cartItemSessionDTOs);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code GET /cartItems/display} : get all cartItems with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all cartItems.
     */
    @GetMapping("/cartItems/display")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<CartItemDisplayDTO>> getPersistedCartItems() {
        log.debug("REST request to get persisted CartItems");

        final List<CartItemDisplayDTO> cartItems = cartItemService.getPersistedCartItems();

        return ResponseEntity.ok().body(cartItems);
    }
}
