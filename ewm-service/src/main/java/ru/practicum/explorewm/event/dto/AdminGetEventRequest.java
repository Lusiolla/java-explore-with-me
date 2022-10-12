package ru.practicum.explorewm.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.explorewm.CustomDateTimeDeserializer;
import ru.practicum.explorewm.event.State;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NotNull
public class AdminGetEventRequest {
    private Set<Long> users;
    private Set<State> states;
    private Set<Long> categories;
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime rangeStart;
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime rangeEnd;
    private int from;
    private int size;
}
