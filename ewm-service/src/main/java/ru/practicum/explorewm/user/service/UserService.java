package ru.practicum.explorewm.user.service;

import ru.practicum.explorewm.user.model.User;

import java.util.Collection;

public interface UserService {

    User add(User newUser);

    Collection<User> get(Collection<Long> ids, Integer from, Integer size);

    void delete(Long userId);
}
