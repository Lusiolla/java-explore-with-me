package ru.practicum.explorewm.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewm.CustomDateTimeSerializer;
import ru.practicum.explorewm.category.model.Category;
import ru.practicum.explorewm.event.State;
import ru.practicum.explorewm.event.location.dto.LocationDto;
import ru.practicum.explorewm.user.dto.UserShort;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFull {
    private String annotation;
    private Category category;
    private int confirmedRequests;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime createdOn;
    private String description;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime eventDate;
    private Long id;
    private UserShort initiator;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private int views;
}
