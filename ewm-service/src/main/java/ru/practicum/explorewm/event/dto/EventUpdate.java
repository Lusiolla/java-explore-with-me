package ru.practicum.explorewm.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import ru.practicum.explorewm.dataio.CustomDateTimeDeserializer;
import ru.practicum.explorewm.event.validator.InTwoHours;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdate {
    private Long eventId;
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    @Future
    @InTwoHours
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime eventDate;
    @NotNull
    private Boolean paid;
    @NotNull
    private Integer participantLimit;
    @Size(min = 3, max = 120)
    private String title;
}
