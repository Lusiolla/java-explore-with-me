package ru.practicum.explorewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.category.CategoryMapper;
import ru.practicum.explorewm.category.dto.CategoryDto;
import ru.practicum.explorewm.category.dto.CreateCategory;
import ru.practicum.explorewm.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Validated
public class AdminCategoryController {

    private final CategoryService service;

    @PostMapping
    public CategoryDto add(@Valid @RequestBody CreateCategory category) {
        return service.add(CategoryMapper.mapToCategory(category));
    }

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto category) {
        return service.update(CategoryMapper.mapToCategory(category));
    }

    @DeleteMapping("{categoryId}")
    public void delete(@NotNull @PathVariable Long categoryId) {
        service.delete(categoryId);
    }
}
