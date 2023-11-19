package com.cupme.service.mapper;

import com.cupme.domain.Category;
import com.cupme.service.dto.CategoryDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Category} and its DTO called {@link Category}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class CategoryMapper {

    public List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categories) {
        return categories.stream().filter(Objects::nonNull).map(this::categoryToCategoryDTO).collect(Collectors.toList());
    }

    public CategoryDTO categoryToCategoryDTO(Category category) {
        return new CategoryDTO(category);
    }

    public List<Category> categoryDTOsToCategories(List<CategoryDTO> categoryDTOS) {
        return categoryDTOS.stream().filter(Objects::nonNull).map(this::categoryDTOToCategory).collect(Collectors.toList());
    }

    public Category categoryDTOToCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        } else {
            Category category = new Category();
            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());
            category.setProducts(categoryDTO.getProducts());
            category.setProtocols(categoryDTO.getProtocols());

            return category;
        }
    }
}
