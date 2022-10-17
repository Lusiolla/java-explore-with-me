package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsCount {
    private String app;
    private Long count;
}
