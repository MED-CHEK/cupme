package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.CartService;
import com.cupme.service.dto.CartDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private final CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * {@code GET /carts} : get all carts with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all carts.
     */
    @GetMapping("/carts")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        log.debug("REST request to get all public Cart names");

        final List<CartDTO> cartes = cartService.getCarts();
        return ResponseEntity.ok().body(cartes);
    }

    /**
     * {@code GET /cart} : get cart for user
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all carts.
     */
    @GetMapping("/cart")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartDTO> getCartForUser() {
        log.debug("REST request to get Cart for user");

        final CartDTO cart = cartService.getCartForUser();
        return ResponseEntity.ok().body(cart);
    }

    /**
     * {@code GET /carts/:id} : get the "id" cart.
     * @param id the id of the cartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carts/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartDTO> getCart(long id) {
        log.debug("REST request to get Cart : {}", id);

        final CartDTO cart = cartService.getCart(id);
        return ResponseEntity.ok().body(cart);
    }

    /**
     * {@code POST  /carts} : Create a new cart.
     *
     * @param cartDTO the cartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartDTO, or with status {@code 400 (Bad Request)} if the cart has already an ID.
     */
    @PostMapping("/carts")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to save Cart : {}", cartDTO);

        if (cartDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final CartDTO result = cartService.createCart(cartDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /carts} : Updates an existing cart.
     *
     * @param cartDTO the cartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartDTO,
     * or with status {@code 400 (Bad Request)} if the cartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartDTO couldn't be updated.
     */
    @PutMapping("/carts")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartDTO> updateCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to update Cart : {}", cartDTO);

        if (cartDTO.getId() == null) {
            return createCart(cartDTO);
        }

        final CartDTO result = cartService.updateCart(cartDTO);
        return ResponseEntity.ok().body(result);
    }
}
