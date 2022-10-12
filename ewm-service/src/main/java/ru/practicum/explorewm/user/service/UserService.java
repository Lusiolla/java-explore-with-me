package ru.practicum.explorewm.user.service;

import ru.practicum.explorewm.user.dto.UserDto;
import ru.practicum.explorewm.user.model.User;

import java.util.Collection;

public interface UserService {

    UserDto add(User newUser);

    Collection<UserDto> get(Collection<Long> ids, Integer from, Integer size);

    void delete(Long userId);
}
