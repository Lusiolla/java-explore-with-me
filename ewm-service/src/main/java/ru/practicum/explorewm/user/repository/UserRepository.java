package ru.practicum.explorewm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewm.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
