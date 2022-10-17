package ru.practicum.explorewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.category.dto.CategoryDto;
import ru.practicum.explorewm.category.service.CategoryService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryController {

    private final CategoryService service;

    @GetMapping
    public Collection<CategoryDto> getCategories(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getCategories(from, size);
    }

    @GetMapping("{categoryId}")
    public CategoryDto getById(@NotNull @PathVariable Long categoryId) {
        return service.getById(categoryId);
    }
}
