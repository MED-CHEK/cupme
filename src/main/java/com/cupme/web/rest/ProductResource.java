package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.ProductService;
import com.cupme.service.dto.ProductDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    /**
     * {@code GET /products} : get all products with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products.
     */
    @GetMapping("/products")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.debug("REST request to get all public Product names");

        final List<ProductDTO> productes = productService.getProducts();
        return ResponseEntity.ok().body(productes);
    }

    /**
     * {@code GET /products/:id} : get the "id" product.
     * @param id the id of the productDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProductDTO> getProduct(long id) {
        log.debug("REST request to get Product : {}", id);

        final ProductDTO product = productService.getProduct(id);
        return ResponseEntity.ok().body(product);
    }

    /**
     * {@code POST  /products} : Create a new product.
     *
     * @param productDTO the productDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDTO, or with status {@code 400 (Bad Request)} if the product has already an ID.
     */
    @PostMapping("/products")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to save Product : {}", productDTO);

        if (productDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final ProductDTO result = productService.createProduct(productDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /products} : Updates an existing product.
     *
     * @param productDTO the productDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDTO,
     * or with status {@code 400 (Bad Request)} if the productDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDTO couldn't be updated.
     */
    @PutMapping("/products")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to update Product : {}", productDTO);

        if (productDTO.getId() == null) {
            return createProduct(productDTO);
        }

        final ProductDTO result = productService.updateProduct(productDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /products/:id} : delete the "id" product.
     *
     * @param id the id of the productDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteProduct(long id) {
        log.debug("REST request to delete Product : {}", id);

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
