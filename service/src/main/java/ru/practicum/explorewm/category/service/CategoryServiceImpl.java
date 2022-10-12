package ru.practicum.explorewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewm.category.CategoryMapper;
import ru.practicum.explorewm.category.CategoryRepository;
import ru.practicum.explorewm.category.dto.CategoryDto;
import ru.practicum.explorewm.category.model.Category;
import ru.practicum.explorewm.exception.ObjectAlreadyExistException;
import ru.practicum.explorewm.exception.ObjectNotFoundException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public CategoryDto add(Category category) {
        try {
            return CategoryMapper.mapToCategoryDto(repository.save(category));
        } catch (RuntimeException e) {
            throw new ObjectAlreadyExistException("name", category.getName());
        }
    }

    @Override
    public CategoryDto update(Category category) {
        return add(category);
    }

    @Override
    public CategoryDto getById(Long id) {
        return CategoryMapper.mapToCategoryDto(repository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Category", id)));
    }

    @Override
    public Collection<CategoryDto> getCategories(int from, int size) {
        return CategoryMapper.mapToLisCategoryDto(repository.findAll(PageRequest.of(from / size, size)));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
