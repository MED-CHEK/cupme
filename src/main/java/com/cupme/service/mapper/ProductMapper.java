package com.cupme.service.mapper;

import com.cupme.domain.Product;
import com.cupme.service.dto.ProductDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Product} and its DTO called {@link Product}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class ProductMapper {

    public List<ProductDTO> productsToProductDTOs(List<Product> products) {
        return products.stream().filter(Objects::nonNull).map(this::productToProductDTO).collect(Collectors.toList());
    }

    public ProductDTO productToProductDTO(Product product) {
        return new ProductDTO(product);
    }

    public List<Product> productDTOsToProducts(List<ProductDTO> productDTOS) {
        return productDTOS.stream().filter(Objects::nonNull).map(this::productDTOToProduct).collect(Collectors.toList());
    }

    public Product productDTOToProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        } else {
            Product product = new Product();
            product.setId(productDTO.getId());
            product.setName(productDTO.getName());
            product.setType(productDTO.getType());
            product.setShortDescription(productDTO.getShortDescription());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setWeight(productDTO.getWeight());
            product.setSize(productDTO.getSize());
            product.setWidth(productDTO.getWidth());
            product.setHeight(productDTO.getHeight());
            product.setStock(productDTO.getStock());
            product.setTags(productDTO.getTags());
            product.setCategories(productDTO.getCategories());
            product.setProtocols(productDTO.getProtocols());

            return product;
        }
    }
}
