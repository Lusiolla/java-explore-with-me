package ru.practicum.explorewm.user.mapper;

import ru.practicum.explorewm.user.dto.UserCreate;
import ru.practicum.explorewm.user.dto.UserShort;
import ru.practicum.explorewm.user.model.User;


public class UserMapper {

    public static User mapToUser(UserCreate request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    public static UserShort mapToUserShort(User user) {
        UserShort response = new UserShort();
        response.setId(user.getId());
        response.setName(user.getName());
        return response;
    }
}
