package ru.practicum.explorewm.user;

import ru.practicum.explorewm.user.dto.UserCreate;
import ru.practicum.explorewm.user.dto.UserDto;
import ru.practicum.explorewm.user.dto.UserShort;
import ru.practicum.explorewm.user.model.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserMapper {

    // из create в entity
    public static User mapToUser(UserCreate request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    // из entity в dto
    public static UserDto mapToUserDto(User user) {
        UserDto response = new UserDto();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }

    // из entity в short
    public static UserShort mapToUserShort(User user) {
        UserShort response = new UserShort();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }

    // из iterable в dto
    public static Collection<UserDto> mapToLisUserDto(Iterable<User> users) {
        Collection<UserDto> response = new ArrayList<>();
        for (User user : users) {
            response.add(mapToUserDto(user));
        }
        return response;
    }
}
