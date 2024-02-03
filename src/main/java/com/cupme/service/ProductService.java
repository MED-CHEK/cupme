package com.cupme.service;

import com.cupme.repository.ProductRepository;
import com.cupme.service.dto.ProductCartDTO;
import com.cupme.service.dto.ProductDTO;
import com.cupme.service.mapper.ProductMapper;
import com.cupme.service.utils.AssetFilesService;
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

    private final AssetFilesService assetFilesService;
    private final CacheManager cacheManager;

    public ProductService(
        ProductRepository productRepository,
        ProductMapper productMapper,
        AssetFilesService assetFilesService,
        CacheManager cacheManager
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.assetFilesService = assetFilesService;
        this.cacheManager = cacheManager;
    }

    public List<ProductCartDTO> getProducts() {
        List<ProductCartDTO> productCartDTOs = productMapper.productsToProductCartDTO(productRepository.findAll());
        productCartDTOs.forEach(protocolDTO -> {
            protocolDTO.getPicture().setFile(assetFilesService.getFile(protocolDTO.getPicture().getFile()));
        });

        return productCartDTOs;
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
