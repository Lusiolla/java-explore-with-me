package ru.practicum.explorewm.category.service;

import ru.practicum.explorewm.category.dto.CategoryDto;
import ru.practicum.explorewm.category.model.Category;

import java.util.Collection;

public interface CategoryService {

    CategoryDto add(Category category);

    CategoryDto update(Category category);

    CategoryDto getById(Long id);

    Collection<CategoryDto> getCategories(int from, int size);

    void delete(long id);

}
