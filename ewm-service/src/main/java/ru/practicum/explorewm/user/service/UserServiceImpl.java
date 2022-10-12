package ru.practicum.explorewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewm.exception.ObjectAlreadyExistException;
import ru.practicum.explorewm.exception.ObjectNotFoundException;
import ru.practicum.explorewm.user.UserMapper;
import ru.practicum.explorewm.user.UserRepository;
import ru.practicum.explorewm.user.dto.UserDto;
import ru.practicum.explorewm.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional
    public UserDto add(User newUser) {
        try {
            return UserMapper.mapToUserDto(repository.save(newUser));
        } catch (RuntimeException e) {
            throw new ObjectAlreadyExistException("email", newUser.getEmail());
        }
    }

    @Override
    public Collection<UserDto> get(Collection<Long> ids, Integer from, Integer size) {
        if (ids == null || ids.isEmpty()) {
            return UserMapper.mapToLisUserDto(repository.findAll(PageRequest.of(from / size, size)));
        } else {
            return ids.stream()
                    .map(id -> repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                            "User", id)))
                    .map(UserMapper::mapToUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        repository.deleteById(userId);
    }
}
