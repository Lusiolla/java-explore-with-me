package ru.practicum.explorewm.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.explorewm.CustomDateTimeSerializer;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
@Component
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConditionsNotMetException.class)
    @ResponseBody
    protected ResponseEntity<Object> handleConditionsNotMet(ConditionsNotMetException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.FORBIDDEN,
                "The conditions for performing the operation are not met.",
                ex.getMessage(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Object> handleUserNotFound(ObjectNotFoundException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                "The required object was not found.",
                 ex.getMessage(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(ObjectAlreadyExistException.class)
    @ResponseBody
    protected ResponseEntity<Object> handleUserAlreadyExist(ObjectAlreadyExistException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated",
                ex.getMessage(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "The object is not valid.",
                ex.getLocalizedMessage(),
                LocalDateTime.now(),
                errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "The object is not valid.",
                ex.getLocalizedMessage(),
                LocalDateTime.now(),
                errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }


    @Data
    private static class ApiError {

        private HttpStatus status;
        private String reason;
        private String message;
        @JsonSerialize(using = CustomDateTimeSerializer.class)
        private LocalDateTime timestamp;
        private List<String> errors;

        public ApiError(HttpStatus status, String reason, String message, LocalDateTime timestamp, List<String> errors) {
            super();
            this.status = status;
            this.reason = reason;
            this.message = message;
            this.timestamp = timestamp;
            this.errors = errors;
        }
    }
}
