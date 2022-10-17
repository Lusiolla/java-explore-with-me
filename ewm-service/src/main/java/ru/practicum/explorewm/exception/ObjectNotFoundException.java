package ru.practicum.explorewm.exception;


public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String nameObject, long id) {
        super(nameObject + " with id=" + id + " was not found.");
    }
}
