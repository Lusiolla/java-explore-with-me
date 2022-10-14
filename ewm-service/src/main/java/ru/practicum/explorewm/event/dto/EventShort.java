package ru.practicum.explorewm.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewm.dataio.CustomDateTimeSerializer;
import ru.practicum.explorewm.category.dto.CategoryDto;
import ru.practicum.explorewm.user.dto.UserShort;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShort {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime eventDate;
    private Long id;
    private UserShort initiator;
    private Boolean paid;
    private String title;
    private int views;
}
