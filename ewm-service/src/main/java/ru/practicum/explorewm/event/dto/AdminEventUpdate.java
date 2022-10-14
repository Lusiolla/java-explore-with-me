package ru.practicum.explorewm.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import ru.practicum.explorewm.dataio.CustomDateTimeDeserializer;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventUpdate extends EventUpdate {
    private String annotation;
    private Long category;
    private String description;
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime eventDate;
    private Boolean paid;
    private Integer participantLimit;
    private String title;

}
