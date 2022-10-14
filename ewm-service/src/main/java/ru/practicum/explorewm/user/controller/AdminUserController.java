package ru.practicum.explorewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewm.user.mapper.UserMapper;
import ru.practicum.explorewm.user.dto.UserCreate;
import ru.practicum.explorewm.user.model.User;
import ru.practicum.explorewm.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Validated
public class AdminUserController {

    private final UserService service;

    @GetMapping
    public Collection<User> get(
            @RequestParam(name = "ids", required = false) Set<Long> ids,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return service.get(ids, from, size);
    }

    @PostMapping
    public User add(@Valid @RequestBody UserCreate newUser) {
        return service.add(UserMapper.mapToUser(newUser));
    }


    @DeleteMapping("{userId}")
    public void delete(@NotNull @PathVariable Long userId) {
        service.delete(userId);
    }
}


