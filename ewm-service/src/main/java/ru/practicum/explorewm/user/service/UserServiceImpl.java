package ru.practicum.explorewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewm.exception.ObjectAlreadyExistException;
import ru.practicum.explorewm.user.repository.UserRepository;
import ru.practicum.explorewm.user.model.User;

import javax.persistence.PersistenceException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional
    public User add(User newUser) {
        try {
            return repository.save(newUser);
        } catch (PersistenceException e) {
            throw new ObjectAlreadyExistException("email", newUser.getEmail());
        }
    }

    @Override
    public Collection<User> get(Collection<Long> ids, Integer from, Integer size) {
        if (ids == null || ids.isEmpty()) {
            return repository.findAll(PageRequest.of(from / size, size)).toList();
        } else {
            return repository.findAllById(ids);
        }
    }

    @Override
    public void delete(Long userId) {
        repository.deleteById(userId);
    }
}
