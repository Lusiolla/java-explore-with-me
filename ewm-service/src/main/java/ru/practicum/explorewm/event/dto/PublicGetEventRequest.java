package ru.practicum.explorewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NotNull
public class PublicGetEventRequest {
    private String text;
    private Set<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private SortRequest sortRequest;
    private int from;
    private int size;
}
