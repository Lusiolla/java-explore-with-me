package ru.practicum.explorewm.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewm.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
