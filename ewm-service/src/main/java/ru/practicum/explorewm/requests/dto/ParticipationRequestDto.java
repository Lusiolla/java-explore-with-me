package ru.practicum.explorewm.requests.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewm.CustomDateTimeSerializer;
import ru.practicum.explorewm.requests.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private Status status;


}
