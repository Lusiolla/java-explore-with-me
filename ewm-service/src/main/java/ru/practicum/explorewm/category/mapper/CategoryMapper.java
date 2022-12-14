package ru.practicum.explorewm.category.mapper;

import ru.practicum.explorewm.category.dto.CategoryDto;
import ru.practicum.explorewm.category.dto.CreateCategory;
import ru.practicum.explorewm.category.model.Category;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryMapper {

    public static Category mapToCategory(CreateCategory request) {
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }

    public static Category mapToCategory(CategoryDto request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName());
        return category;
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        CategoryDto response = new CategoryDto();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }

    public static Collection<CategoryDto> mapToLisCategoryDto(Iterable<Category> categories) {
        Collection<CategoryDto> response = new ArrayList<>();
        for (Category category : categories) {
            response.add(mapToCategoryDto(category));
        }
        return response;
    }
}
