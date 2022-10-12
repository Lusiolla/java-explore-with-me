package ru.practicum.explorewm.exception;

public class ObjectAlreadyExistException extends RuntimeException {
    public ObjectAlreadyExistException(String keyName, String value) {

        super("Повторяющееся значение ключа нарушает ограничение уникальности. " +
                "Ключ (" + keyName + ") =" + value + " уже существует.");
    }
}
