package com.cupme.service;

import com.cupme.repository.ProductRepository;
import com.cupme.service.dto.ProductDTO;
import com.cupme.service.mapper.ProductMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing products.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    private final CacheManager cacheManager;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CacheManager cacheManager) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.cacheManager = cacheManager;
    }

    public List<ProductDTO> getProducts() {
        return productMapper.productsToProductDTOs(productRepository.findAll());
    }

    public ProductDTO getProduct(long id) {
        return productMapper.productToProductDTO(productRepository.findById(id).get());
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        return productMapper.productToProductDTO(productRepository.save(productMapper.productDTOToProduct(productDTO)));
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        return productMapper.productToProductDTO(productRepository.save(productMapper.productDTOToProduct(productDTO)));
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
