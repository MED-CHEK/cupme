package com.cupme.service.mapper;

import com.cupme.domain.Product;
import com.cupme.service.dto.PictureDTO;
import com.cupme.service.dto.ProductCartDTO;
import com.cupme.service.dto.ProductDTO;
import com.cupme.service.dto.ProtocolCartDTO;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    public Set<Product> productDTOsToProducts(Set<ProductDTO> productDTOS) {
        return productDTOS.stream().filter(Objects::nonNull).map(this::productDTOToProduct).collect(Collectors.toSet());
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

    public Product productToProductCartDTO(ProductCartDTO productCartDTO) {
        if (productCartDTO == null) {
            return null;
        } else {
            Product product = new Product();
            product.setId(productCartDTO.getId());
            product.setName(productCartDTO.getName());
            product.setPrice(productCartDTO.getPrice());

            return product;
        }
    }

    public ProductCartDTO productToProductCartDTO(Product product) {
        if (product == null) {
            return null;
        } else {
            ProductCartDTO productCartDTO = new ProductCartDTO();
            productCartDTO.setId(product.getId());
            productCartDTO.setName(product.getName());
            productCartDTO.setPrice(product.getPrice());
            productCartDTO.setPicture(new PictureDTO(product.getPictures().stream().filter(p -> p.getMain()).findFirst().orElse(null)));

            return productCartDTO;
        }
    }

    public List<ProductCartDTO> productsToProductCartDTO(List<Product> products) {
        return products.stream().filter(Objects::nonNull).map(this::productToProductCartDTO).collect(Collectors.toList());
    }
}
