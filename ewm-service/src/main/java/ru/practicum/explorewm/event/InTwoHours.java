package ru.practicum.explorewm.event;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = InTwoHoursValidator.class)
@Documented
public @interface InTwoHours {
    String message() default "{InTwoHours.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
