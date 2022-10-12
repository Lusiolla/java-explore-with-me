package ru.practicum.explorewm.event;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class InTwoHoursValidator implements ConstraintValidator<InTwoHours, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
        if (localDateTime != null) {
            if ((localDateTime.equals(LocalDateTime.now().plusHours(2)))) {
                return true;
            }
            return localDateTime.isAfter(LocalDateTime.now().plusHours(2));
        }
        return false;
    }
}
